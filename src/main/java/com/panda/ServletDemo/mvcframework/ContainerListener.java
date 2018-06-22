package com.panda.ServletDemo.mvcframework;

import com.panda.ServletDemo.mvcframework.annotation.MyAutoWired;
import com.panda.ServletDemo.mvcframework.annotation.MyController;
import com.panda.ServletDemo.mvcframework.annotation.MyRequsetMapping;
import com.panda.ServletDemo.mvcframework.annotation.MyService;
import com.panda.ServletDemo.mvcframework.bean.Requestor;
import com.panda.ServletDemo.mvcframework.enums.MyRequestMethod;
import com.panda.ServletDemo.util.StringUtil;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.Version;
import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * web容器监听器
 * @author pcongda
 * @version 1.0
 * 
 */
@WebListener("AppListener")
public class ContainerListener implements ServletContextListener{

	private static final Logger logger = LoggerFactory.getLogger(ContainerListener.class);
	
	//默认配置文件名称
	private static final String SETTING_NAME = "application.properties";
	
	private Properties prop = new Properties();
	
	//保存所有className
	private List<String> classNames = new ArrayList<>();
	
	//Ioc容器
	private Map<String, Object> iocContainer = new HashMap<>();	
	
	//http请求与方法映射
    private static final Map<Requestor, Handler> actionMap = new LinkedHashMap<>();

    
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		//获取ServletContext
		ServletContext servletContext = sce.getServletContext();
		//thymeleaf 测试
		servletContext.setAttribute("cxtPath", "thymeleaf测试正常");
		//mvc初始化
		initMvcFramework();
	    //添加Servlet静态资源映射
	    addServletMapping(servletContext);
	    // 注册freemarkerServlet
		//RegisterFreemarker(servletContext);
	}
	
	private void addServletMapping(ServletContext sc){
		//通过default servlet 映射静态资源
		ServletRegistration regist = sc.getServletRegistration("default");
		regist.addMapping("/resources/static/*");
		//regist.addMapping("/templates/*");
		regist.addMapping("/favicon.ico");
		//regist.addMapping("/WEB-INF/resources/*");
	}
	
	private void initMvcFramework() {
		//1.容器启动时，读取application.properties
		doLoadConfig(SETTING_NAME);
		
		//2.扫描带有注解的类
		doScanner(prop.getProperty("scanBasePackge"));
		
		//3.实例化相关类，添加至Ioc容器中
		doInstance(classNames);
		
		//4.实现DI,依赖注入
		doAutoWired();
		
		//5.构造HandleMapping,将每一个url映射到每一个method上
		initHandleMapping();
	}
	
	/**
	 * 读取配置文件
	 * @author pcongda
	 */
	private void doLoadConfig(String location){
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(location);
		try {
			prop.load(is);
		}catch (IOException e) {
			logger.error("加载"+location+"配置文件出错!",e);
		}finally{
			try {
				is.close();
			} catch (IOException e) {
				logger.error("关闭输入流出错!",e);
			}
		}
	}
	
	/**
	 * 递归扫描基础包下所有子包，以/开头,从classpath下获取,toURI() 转义空格或中文
	 * @author pcongda
	 */
	private void doScanner(String packageName) {
		URI url = null;
		try {
			url = this.getClass().getClassLoader().getResource("/" + packageName.replaceAll("\\.", "/")).toURI();
		} catch (URISyntaxException e) {
			logger.error("获取基础包url出错!",e);
		}
		//转为文件对象
		File dir = new File(url.getPath());
		//循环文件
		for (File file : dir.listFiles()) {
			//是文件夹就递归
			if(file.isDirectory()) {
				doScanner(packageName + "." + file.getName());
			}else {
				//是文件就得到文件全类名,上级名+文件名(去掉后缀)
  				String className = packageName + "." + file.getName().replace(".class", "");
  				//放入集合中
  				classNames.add(className);
			}
		}
	}
	
	/**
	 * 反射实例化相关类，放入Ioc容器中
	 * @author pcongda
	 */
	private void doInstance(List<String> classNames) {
		//集合中类名为空，直接返回
		if(classNames.size() == 0) return ;
		//循环集合
		for (String name : classNames) {
			try {
				Class<?> clazz = Class.forName(name);
				//如果类上标注有controller注解
				if(clazz.isAnnotationPresent(MyController.class)) {
					//得到该类的类名
					String beanName = clazz.getSimpleName();
					//保存到Ioc容器中，key为该类名的小写，value为该类的实例对象
					iocContainer.put(StringUtil.toLower(beanName), clazz.newInstance());
				//如果类上标注有service注解
				}else if(clazz.isAnnotationPresent(MyService.class)) {
					//得到注解上的value
					String beanName = clazz.getAnnotation(MyService.class).value();
					//value是否有值
					if(StringUtils.isNotEmpty(beanName.trim())) {
						//有值，使用用户的命名,添加到Ioc容器中					
						iocContainer.put(beanName, clazz.newInstance());
						continue;			
					}else {
						//用户没有命名，默认使用类名的小写
						iocContainer.put(StringUtil.toLower(clazz.getSimpleName()), clazz.newInstance());
					}
					//注入controller的对象是接口,应该使用其实现接口的全称
					Class<?>[] interfaces =  clazz.getInterfaces();
					for (Class<?> i : interfaces) {
						iocContainer.put(i.getName(), clazz.newInstance());
					}	
				}else {
					//其他跳过
					continue;
				}
			} catch (Exception e) {
				logger.error("获取class字节码文件对象失败",e);
			} 
		}
		
	}
	
	/**
	 * 类的依赖注入
	 * @author pcongda
	 */
	private void doAutoWired() {
		//ioc容器没有对象,直接返回
		if(iocContainer.isEmpty()) return ;
		
		//循环ioc容器，得到entry对象
		for (Entry<String, Object> entry : iocContainer.entrySet()) {
			//获取类中所有的字段
			Field[] fields = entry.getValue().getClass().getDeclaredFields();
			for (Field field : fields) {
				//字段上没标注Autuowried直接跳过
				if(!field.isAnnotationPresent(MyAutoWired.class)){
					continue;
				}
				//获取自动注入的这个注解的value
				String fieldName = field.getAnnotation(MyAutoWired.class).value().trim();
				//如果value为空,默认使用该字段的名称首字母小写
				if(StringUtils.isEmpty(fieldName.trim())) {
					fieldName = StringUtil.toLower(field.getType().getSimpleName());
				}
				field.setAccessible(true);
				try {
					//给该字段赋值
					field.set(entry.getValue(), iocContainer.get(fieldName));
				} catch (Exception e) {
					logger.error("controller依赖注入失败",e);
				}
			}
		}
	}


	/**
	 * Controller构造HandleMapping,将每一个Url映射成每一个Method
	 * @author pcongda
	 */
	private void initHandleMapping() {
		//Ioc容器为空，直接返回
		if(iocContainer.isEmpty()) return;
		
		//普通 Action Map
        Map<Requestor, Handler> commonActionMap = new HashMap<>(); 
        //Action Map(带正则)
        Map<Requestor, Handler> regexpActionMap = new HashMap<>(); 
		
		//遍历Ioc容器
		for (String key : iocContainer.keySet()) {
			//先获取每一个实例的字节码对象
			Class<?> clazz = iocContainer.get(key).getClass();
			
			//类上的访问路径
			String baseUrl = "";
			
			//不是controller,跳过
			if(!clazz.isAnnotationPresent(MyController.class)){
				continue;
			}
			//获取类上的requestMapping注解的值
			if(clazz.isAnnotationPresent(MyRequsetMapping.class)){
				baseUrl = clazz.getAnnotation(MyRequsetMapping.class).value();
			}
			
			//获取该字节码对象方法对象(只获取public的就行了)
			Method[] methods =  clazz.getMethods();
			
			// 遍历所有方法，找到标注requsetMapping注解的
			for (Method method : methods) {
				//不带有RequsetMapping,跳过
				if(!method.isAnnotationPresent(MyRequsetMapping.class)){
					continue;
				}
				//获取RequsetMapping注解的value,多个/转义为一个/,总的路径为:类上的值+方法上的值
				String mappingUrl = ("/" + baseUrl + method.getAnnotation(MyRequsetMapping.class).value()).replaceAll("/+", "/");
				//获取方法上的注解上指定的方法
				MyRequestMethod[] methodList = method.getAnnotation(MyRequsetMapping.class).method();
				putActionMap(methodList,mappingUrl,clazz,method,commonActionMap,regexpActionMap);
			}
		}
	}
	
	//放置值至actionMap中
	private void putActionMap(MyRequestMethod[] reqMethod, String reqPath, Class<?> actionClass, Method actionMethod, Map<Requestor, Handler> commonActionMap, Map<Requestor, Handler> regexpActionMap) {
		//判断请求路径中是否带有占位符
        if (reqPath.matches(".+\\{\\w+\\}.*")) {
        	String mReqPath = reqPath;
            //将请求路径中的占位符 {\w+} 转换为正则表达式 (\\w+)
        	reqPath = StringUtil.replaceAll(reqPath, "\\{\\w+\\}", "(\\\\w+)");
        	
            Pattern pattern = Pattern.compile("\\{\\w+\\}");
            Matcher matcher = pattern.matcher(mReqPath);
            List<String> pathParams=new LinkedList<>();
            //得到参数名，保存起来
        	while (matcher.find()) {
                pathParams.add(matcher.group(0).replaceAll("\\{", "").replaceAll("\\}", ""));
			}
        	System.out.println(pathParams);
            regexpActionMap.put(new Requestor(reqPath, reqMethod), new Handler(actionClass, actionMethod,0,pathParams));
        } else {
            commonActionMap.put(new Requestor(reqPath, reqMethod), new Handler(actionClass, actionMethod,0));
        }
        actionMap.putAll(commonActionMap);
        actionMap.putAll(regexpActionMap); 
    }
	
	/**
	 * @author pcongda
     * 获取 Action Map
     */
    public static Map<Requestor, Handler> getActionMap() {
        return actionMap;
    }
	
	//获取thymeleaf模版
 	public static TemplateEngine templateEngine(ServletContext servletContext) {
        TemplateEngine engine = new TemplateEngine();
	    engine.addDialect(new LayoutDialect());
        engine.setTemplateResolver(templateResolver(servletContext));
        return engine;
    }
	
 	//thymeleaf模版配置信息
 	private static ITemplateResolver templateResolver(ServletContext servletContext) {
         ServletContextTemplateResolver resolver = new ServletContextTemplateResolver(servletContext);
         resolver.setCharacterEncoding("UTF-8");
         resolver.setTemplateMode("HTML");
	     //配置thymeleaf 解析基础包路径
         resolver.setPrefix("/templates/");
         resolver.setSuffix(".html");
	     //禁用缓存
         resolver.setCacheable(false);
         return resolver;
    }
 	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		sce = null;
	}

	/**
	 * freemarker 配置
	 * @param sc
	 */
	public static Configuration addFreemarkerConfig(ServletContext sc){

		// 1.基于类路径下加载 模版目录
//		String path = ContainerListener.class.getResource("/").getPath();
//		path = path.substring(1, path.indexOf("classes"));
//		System.out.println(path);
//		File ftlPathDir = new File(path+File.separator+"ftl");
//		System.out.println(ftlPathDir.getPath());
//		System.out.println(ftlPathDir.getName());

/////////////////////////////////////////////////////////////////////////////////////////////

		//设置版本
		Configuration config = new Configuration(Configuration.VERSION_2_3_23);
		// 2.基于ServletContext 上下文 加载模版目录
		config.setServletContextForTemplateLoading(sc,"/templates/ftl/");
		//时区
		config.setLocale(Locale.CHINA);
		//编码
		config.setEncoding(Locale.CHINA, "UTF-8");
		config.setDefaultEncoding("UTF-8");
		config.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		config.setClassicCompatible(true);
		config.setTemplateUpdateDelayMilliseconds(0);
		// 指定模板如何查看数据模型
		config.setObjectWrapper(new DefaultObjectWrapper(new Version("2.3.23")));

	///////////////////////////////////////////////////////////////

//		try {
//			// 1.基于类路径下加载 模版目录
//			config.setDirectoryForTemplateLoading(ftlPathDir);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		return config;
	}

	/**
	 * 注册 及 配置 freemarkerServlet
	 * @param sc
	 */
	private void registerFreemarker(ServletContext sc){
		//注册freemarkerServlet
		ServletRegistration freemarker = sc.addServlet("freemarker","freemarker.ext.servlet.FreemarkerServlet");
		// 基本解析路径
		freemarker.setInitParameter("TemplatePath","/templates/ftl/");
		freemarker.setInitParameter("NoCache","true");
		freemarker.setInitParameter("ContentType","text/html;charset=UTF-8");
		freemarker.setInitParameter("template_update_delay", "0");
		freemarker.setInitParameter("default_encoding", "UTF-8");
		freemarker.setInitParameter("number_format", "0.##########");
		freemarker.setInitParameter("freemarker", "*.ftl");
	}
}

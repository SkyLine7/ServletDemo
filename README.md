## 项目特点
- 轻巧依赖少,仅包含JDK及必要的第三方Jar
- 直观剖析模拟SpringMVC 的核心路由分发功能 及 Spring Ioc 功能
- 纯原生Servlet3,纯注解配置,无xml

## 目前的进度
- 已实现 *@RequsetMapping @AutoWired @Service @Controller @PathVariable @RequestParam* 注解
- 请求支持方法参数绑定: String,int/Integer,double/Double,long/Long 四种类型
- 支持请求方法限定，参考SpringMVC @RequestMapping(value = "/xxx.html", **method = {RequestMethod.POST, RequestMethod.GET}**)
- 支持返回页面和JSON数据,及导出Excel(HSSFWorkbook:xls 和 SXSSFWorkbook:xlsx)
- 已整合 Thymeleaf 模版引擎,页面使用Thymeleaf3布局
- 已整合 Freemarker
- 已封装原生JS(ES5)的ajax请求
- 已整合layer和其他杂七杂八的小功能

## 后续的计划
- (批量)文件上传 和 文件下载
- 导出word,pdf格式
- 方法参数绑定支持 JavaBean 类型

### 注意事项
- IDE 建议 IDEA,Eclipse 亦可
- Java 编译时添加参数： -parameters
- JDK 版本使用 8 以上

### 已知BUG
- @AutoWired 依赖注入为null

### 感谢您的阅读
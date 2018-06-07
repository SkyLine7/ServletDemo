## 目前的进度
- 实现了 *@RequsetMapping @AutoWired @Service @Controller @PathVariable* 注解
- 方法参数绑定支持： String,int/Integer,double/Double,long/Long 四种类型
- 支持返回页面和JSON数据
- 整合了 Thymeleaf 模版引擎
- 用原生JS(ES5)封装了一下ajax
- 整合了layer 
- 页面使用Thymeleaf布局
- 其他杂七杂八的小功能

## 后续的计划
- （批量）文件上传 和 文件下载
- 请求方法限定，参考SpringMVC @RequestMapping(value = "/xxx.html", **method = {RequestMethod.POST, RequestMethod.GET}**)
- 方法参数绑定支持 JavaBean 类型

先做这么多，已经够我做的了  =.=


### 注意事项
- Java 编译时添加参数： -parameters
- JDK 版本使用8 以上
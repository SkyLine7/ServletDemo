## 目前的进度
- 已实现 *@RequsetMapping @AutoWired @Service @Controller @PathVariable @RequestParam* 注解
- 支持方法参数绑定： String,int/Integer,double/Double,long/Long 四种类型
- 支持请求方法限定，参考SpringMVC @RequestMapping(value = "/xxx.html", **method = {RequestMethod.POST, RequestMethod.GET}**)
- 支持返回页面和JSON数据
- 已整合 Thymeleaf 模版引擎
- 已封装原生JS(ES5)的ajax请求
- 已整合layer 
- 页面使用Thymeleaf布局
- 其他杂七杂八的小功能

## 后续的计划
- (批量)文件上传 和 文件下载
- 方法参数绑定支持 JavaBean 类型
先做这么多，已经够我做的了  =.=

### 注意事项
- 请 clone dev_1.1分支，master分支未合并
- IDE 建议 IDEA,Eclipse 亦可
- Java 编译时添加参数： -parameters
- JDK 版本使用 8 以上

### 感谢您的阅读，我会继续努力。
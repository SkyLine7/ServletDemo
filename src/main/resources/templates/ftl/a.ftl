<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="Content-Style-Type" content="text/css" />
    <title>Freemarker Test1</title>
</head>
<body>
    <h1>
        <#-- 注释，该内容不会输出 -->
        <#-- ${...} 对象插值，使用数据模型中的部分替代输出 -->
        <#-- <#if user == "Big Joe"> 条件判断，true显示 -->
        Welcome ${user}<#if user == "Big Joe">, our beloved leader</#if>!
    </h1>
    <div>
        <#-- <#list obj as a> ${a} </#list> 集合插值，用于循环输出   -->
        <#list person as p> ${p} </#list>
    </div>
</body>
</html>
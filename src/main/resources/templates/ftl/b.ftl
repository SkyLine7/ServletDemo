<#-- 导入命名空间 -->
<#import "test.ftl" as tFtl>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="Content-Style-Type" content="text/css" />
    <title>Freemarker Test2</title>
</head>
<body>
    <div>
        <#-- <#list mapData?keys as key> </#list> 通过key遍历Map   -->
        <ul>
           <#list mx?keys as key>
               <li>${key}:${mx[key]}</li>
           </#list>
        </ul>

    <#--通过Value遍历Map-->
        <ul style="list-style-type: none;">
            <#list mx?values as value>
                <li>${value}</li>
            </#list>
        </ul>
    </div>

    <#--字符串连接：可以直接嵌套${"hello , ${name}"} ； 也可以用加号${"hello , " + name} -->

    <#-- include指令：引入其他文件 -->
    <#include "foot.ftl" />
</body>
</html>
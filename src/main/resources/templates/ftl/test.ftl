<#-- 导入命名空间 -->
<#import "test.ftl" as tFtl>

<!DOCTYPE html>
<html lang="en">
<head></head>
    <body>
       <#macro addMethod a b >
            result : ${a + b}
       </#macro>
       <#assign otherName="另外一个FreeMarker的变量">
    </body>
</html>
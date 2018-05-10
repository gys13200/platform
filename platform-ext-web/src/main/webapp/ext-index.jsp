<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>


    <c:set var="path" value="${pageContext.request.contextPath}" />

    <script type="text/javascript">
        var project_path = '${path}';
    </script>

    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link type="text/css" rel="stylesheet" href="css/font-awesome.css">
    <link type="text/css" rel="stylesheet" href="css/module.css">
    <link rel="stylesheet" type="text/css" href="${path}/js/extjs/packages/theme-neptune/resources/theme-neptune-all.css">

    <!-- The line below must be kept intact for Sencha Cmd to build your application -->
    <script id="microloader" type="text/javascript" src="${path}/js/extjs/bootstrap.js"></script>
    <script id="renderer" type="text/javascript" src="${path}/js/app/ux/Renderer.js"></script>

    <script type="text/javascript" src="${path}/js/app.js" ></script>
    <title>主页</title>
</head>

<body>

</body>
</html>
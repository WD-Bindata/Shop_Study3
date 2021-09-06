<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form method="post" action="${pageContext.request.contextPath}/login">
    <input type="text" value="admin" name="username">
    <input type="password" name="password" value="123456">
    <input type="submit" value="提交">
</form>
</body>
</html>

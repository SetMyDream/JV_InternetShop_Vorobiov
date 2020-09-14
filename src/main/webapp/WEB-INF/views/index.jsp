<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Index</title>
</head>
<body>
<h1>${message}${time}</h1>
<br> <a href="${pageContext.request.contextPath}/products/all">All products</a> <br/>
<br> <a href="${pageContext.request.contextPath}/users/all">All users</a> <br/>
<br> <a href="${pageContext.request.contextPath}/injectData">Go to injector</a> </br>
<br> <a href="${pageContext.request.contextPath}/orders/admin">Orders as Admin</a> </br>
</body>
</html>

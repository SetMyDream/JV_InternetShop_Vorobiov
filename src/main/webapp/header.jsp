<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Header</title>
</head>
<body>
<a href="${pageContext.request.contextPath}/login">Login</a>
<a href="${pageContext.request.contextPath}/logout">Logout</a>
<a href="${pageContext.request.contextPath}/users/registration">Register</a>
<a href="${pageContext.request.contextPath}/inject">Inject data</a>
<a href="${pageContext.request.contextPath}/">Back to Main</a>
<hr>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<form name="loginForm" method="post" action="${pageContext.request.contextPath}/login">
    Login: <input type="text" name="login"/> <br/>
    Password: <input type="password" name="password"/> <br/>
    <input type="submit" value="Login"/>
</form>
<br/>
<br/>
<h3>Have no account?</h3>
<a href="${pageContext.request.contextPath}
                /users/registration">Register</a>
</body>
</html>

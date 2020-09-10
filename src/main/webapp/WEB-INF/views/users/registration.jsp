<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<h1>Hello! Enter login and password to register as a new user</h1>
<h4 style="color:red">${message}</h4>
<form method="post" action="${pageContext.request.contextPath}/users/registration">
    <input placeholder="Username" type="text" name="username" value="${usernameStorage}"/> <br/>
    <input placeholder="Create password" type="password" name="pass"/> <br/>
    <input placeholder="Repeat password" type="password" name="pass-repeat"/> <br/>
    <button type="submit">Register</button>
</form>
</body>
</html>

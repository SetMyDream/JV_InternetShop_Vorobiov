<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All users</title>
</head>
<body>
<h1>All users page</h1>
<table border=1>
    <tr>
        <th>ID</th>
        <th>Name</th>
    </tr>
    <c:forEach var="user" item="${users}">
        <tr>
            <td>
                <c:out value="${user.id}"/>
            </td>
            <td>
                <c:out value="${user.name}"/>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>

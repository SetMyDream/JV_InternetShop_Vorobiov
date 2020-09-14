<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Your Oder</title>
</head>
<br>
<h2>User ${username}, here list of your order</h2>
<c:forEach var="order" items="${orders}">
    <h3>Order <c:out value="${order.id}"/></h3>
    <a href="${pageContext.request.contextPath}
                /orders/details?id=${order.id}">Details</a>
</c:forEach>
<br/>
<br/> <a href="${pageContext.request.contextPath}/">Back to the main page</a> </br>
</body>
</html>

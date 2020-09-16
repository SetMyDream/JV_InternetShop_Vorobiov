<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Orders</title>
</head>
<body>
<jsp:include page="/header.jsp"></jsp:include>
<h1>All orders (Admin)</h1>
<table border="1">
    <tr>
        <th>Order ID</th>
    </tr>
    <c:forEach var="order" items="${orders}">
        <tr>
            <td>
                <c:out value="${order.id}"/>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}
                /orders/details?id=${order.id}">Details</a>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}
                /orders/delete?id=${order.id}">Delete</a>
            </td>
        </tr>
    </c:forEach>
</table>
<br/>
<a href="${pageContext.request.contextPath}/">Back to the main page</a>
</body>
</html>

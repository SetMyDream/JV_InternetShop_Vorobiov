<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Your Order</title>
</head>
<br>
<h2>User ${username}, here list of your order</h2>
<c:forEach var="order" items="${orders}">
    <h3>Order <c:out value="${order.id}"/></h3>
    <table border="1">
        <tr>
            <th>Item ID</th>
            <th>Name</th>
            <th>Price</th>
        </tr>
        <c:forEach var="product" items="${order.products}">
            <tr>
                <td>
                    <c:out value="${product.id}"/>
                </td>
                <td>
                    <c:out value="${product.name}"/>
                </td>
                <td>
                    <c:out value="${product.price}"/>
                </td>
            </tr>
        </c:forEach>
    </table>
    <a href="${pageContext.request.contextPath}
                /orders/details?id=${order.id}">Details</a>
</c:forEach>
<br/>
<br/> <a href="${pageContext.request.contextPath}/">Back to the main page</a> </br>
</body>
</html>

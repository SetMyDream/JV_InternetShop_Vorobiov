<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Products in cart</title>
</head>
<body>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Price</th>
        <th>Remove</th>
    </tr>
    <c:forEach var="product" items="${products}">
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
            <td>
                <a href="${pageContext.request.contextPath}/shopping-carts/products/remove?id=${product.id}">X</a>
            </td>
        </tr>
    </c:forEach>
</table>
<br/>
<form method="post" action="${pageContext.request.contextPath}/shopping-carts/products/checkout">
    <button type="submit">Checkout</button>
</form>
<br/>
<a href="${pageContext.request.contextPath}/products/all">Add more products</a>
<br/>
<br/>
<a href="${pageContext.request.contextPath}/index">Back to the main page</a>
</body>
</html>

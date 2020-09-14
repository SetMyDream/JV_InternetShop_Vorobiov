<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All products</title>
</head>
<body>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Price</th>
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
                <a href="${pageContext.request.contextPath}/shoppingCarts/products/add?id=${product.id}">Buy</a>
            </td>
        </tr>
    </c:forEach>
</table>
<br/>
<a href="${pageContext.request.contextPath}/shoppingCarts/products">View your cart</a>
<br/>
<br/>
<a href="${pageContext.request.contextPath}/orders/admin">View all orders as Admin</a>
<br/>
<br/>
<a href="${pageContext.request.contextPath}/products/admin">View all products as Admin</a>
<br/>
<br/>
<a href="${pageContext.request.contextPath}/">Back to the main page</a>
</br>
</body>
</html>

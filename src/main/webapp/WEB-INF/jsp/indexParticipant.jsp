<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<head>
    <title>Home page</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="style.css">
</head>
<body>
<div class="top-div">
    <a href="/" class="web-title">KAMP MLADE NADE</a>
</div>

<h2>List of Participants</h2>

<table>
    <tr>
        <th>Name</th>
        <th>Surname</th>
        <th>Email</TH>
    </tr>

    <c:forEach var="part" items="${participants}">
        <li>${part.name} ${part.surname}
        </li>
    </c:forEach>
</table>

</body>
</html>
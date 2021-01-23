<%@ page import="fer.ppj.model.Person" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<head>
    <title>Home page</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.1/css/all.min.css"/>
    <link rel="stylesheet" href="acceptParticipants.css">
</head>
<body>
<div class="top-div">
    <a href="/" class="web-title">KAMP MLADE NADE</a>
</div>

<%
    List<Person> notAcceptedUsers = (List<Person>)request.getAttribute("notAccepted");
    if(notAcceptedUsers == null || notAcceptedUsers.isEmpty()) {
        %>
<h2> </h2>
<h2> </h2>
<h2> </h2>
<h1 style="font-size: 24px">Trenutno nema prijava za obraditi.</h1>
    <%
    }
    else {
%>
<div class="accept-delete-div">
    <table class="container">
        <thead>
        <th><h1>Korisničko ime</h1></th>
        <th><h1>Ime</h1></th>
        <th><h1>Prezime</h1></th>
        <th><h1>Odbij prijavu</h1></th>
        <th><h1>Prihvati prijavu</h1></th>
        </thead>
        <c:forEach var="emp" items="${notAccepted}">
            <div class="not-acc-entry">
                <tr>
                    <td>${emp.nickname}</td>
                    <td>${emp.name}</td>
                    <td>${emp.surname}</td>
                    <td>
                        <form method="post" action="delete?nickname=${emp.nickname}">
                            <button class="fas fa-trash fa-2x" type="submit">
                        </form>
                    </td>

                    <td>
                        <form method="post" action="accept?nickname=${emp.nickname}">
                            <button class="fas fa-check fa-2x" type="submit">
                        </form>
                    </td>
                </tr>
            </div>
        </c:forEach>
    </table>
</div>
<%
    }
%>





<!--
<div style="background-color: white">
    <ol>
        <c:forEach var="emp" items="${notAccepted}">
            <li>${emp.nickname} ${emp.name} ${emp.surname}
                <form method="post" action="delete?nickname=${emp.nickname}">
                    <input type="submit" value="delete">
                </form>
                <form method="post" action="accept?nickname=${emp.nickname}">
                    <input type="submit" value="accept">
                </form>
            </li>
        </c:forEach>
    </ol>
</div>
-->
</body>
</html>

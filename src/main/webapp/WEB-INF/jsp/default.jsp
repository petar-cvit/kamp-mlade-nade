<%@ page import="fer.ppj.model.Organizator" %>
<%@ page import="fer.ppj.model.Person" %>
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<head>
    <style>
        body {margin:0;}

        .navbar {
            overflow: hidden;
            background-color: #333;
            position: absolute;
            top: 0;
            width: 100%;
        }

        .navbar a {
            float: left;
            display: block;
            color: #f2f2f2;
            text-align: center;
            padding: 14px 16px;
            text-decoration: none;
            font-size: 17px;
        }

        .navbar a:hover {
            background: #ddd;
            color: black;
        }
    </style>
    <title>Home page</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="index1.css">
</head>
<body>

<div class="top-div">
    <a href="/" class="web-title">KAMP MLADE NADE</a>
</div>
    <% Person p = (Person) session.getAttribute("user"); %>

<div class="navbar">
    <% if (p instanceof Organizator) { %>
    <a href="acceptParticipants">prihvacanje prijave</a>
    <a href="addActivity">dodaj aktivnost</a>
    <a href="createGroups">stvori grupe</a>
    <a href="signUpOrganizer">dodaj organizatora</a>
    <a href="setTimer">postavi pocetak kampa</a>
    <a href="addActivitiesInTime">dodaj aktivnost u raspored</a>
    <% } %>

    <% if (p != null) { %>
    <a style ="float: right" href="logout">Odjava</a>
    <a style ="float: right">Prijavljeni ste kao <%= p.getNickname() %></a>
    <% if (!(p instanceof Organizator)) { %>
    <a href="schedule">Raspored</a>
    <% } %>

    <% } else { %>
    <a href="signUpAnimator">Registracija za animatora</a>
    <a href="signUpParticipant">Registracija za sudionika</a>
    <a href="login">Prijava</a>
    <% } %>
</div>
</body>
</html>
<%@ page import="fer.ppj.model.Person" %>
<%@ page import="fer.ppj.model.Organizator" %>
<%@ page contentType="text/html; charset=UTF-8" %>
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

<h1>Now that the camp has finished, below you have the option to to leave your impressions and comments. </h1>

<div class="login-box">
    <h2>Ocijenite kamp</h2>

    <form class="form" id="form1" action="/campEnd" method ="POST" autocomplete="off">
        <div>
            <label for="comment" style="color: white">Komentar: </label>
            <textarea name="commentText" required=""  id="comment" style ="width: -webkit-fill-available; height: 7em;"></textarea>
        </div>
        <div>
            <label for="mark" style="color: white">Ocjena: </label>
            <input type="number" id="mark" name="mark" required="" min="1" max="5" style = "width: 7em">
        </div>
        <button type="submit" class="round-button" style = "margin-top: 10%; margin-left: 30%">Ocijeni</button>

    </form>

</div>
</body>
</html>

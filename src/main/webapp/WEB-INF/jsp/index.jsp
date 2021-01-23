<%@ page import="fer.ppj.model.*" %>
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

<script>
    function participant () {

    }


    <% ActivityInTime kamp = (ActivityInTime) request.getAttribute("campStart");%>
    <% if (kamp != null) { %>
    var start = '<%= kamp.getStart()%>';

    // Set the date we're counting down to
    var countDownDate = new Date(start).getTime();

    // Update the count down every 1 second
    var x = setInterval(function() {
        // Get today's date and time
        var now = new Date().getTime();

        // Find the distance between now and the count down date
        var distance = countDownDate - now;

        // Time calculations for days, hours, minutes and seconds
        var days = Math.floor(distance / (1000 * 60 * 60 * 24));
        var hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
        var minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
        var seconds = Math.floor((distance % (1000 * 60)) / 1000);

        // Output the result in an element with id="demo"
        document.getElementById("timer").innerHTML ="Kamp počinje za" + "<br />"+ "<br />"+ "<br />"+ "<br />" + days+  "dana " + hours + "sati "
            + minutes + "minuta " + seconds + "sekundi ";


        // If the count down is over, write some text
        if (distance < 0) {
            clearInterval(x);
            document.getElementById("demo").innerHTML = "EXPIRED";
        }
    }, 1000);
    <% } %>
</script>
<div class="top-div">
    <p class="web-title" >KAMP MLADE NADE</p>
</div>
<% Person p = (Person) session.getAttribute("user"); %>


<div class="navbar">
    <% if (p instanceof Organizator) { %>
    <a href="acceptParticipants">Prihvaćanje prijava</a>
    <a href="addActivity">Dodaj aktivnost</a>
    <a href="createGroups">Grupe</a>
    <a href="signUpOrganizer">Organizatori</a>
    <a href="setTimer">Početak kampa</a>
    <a href="addActivitiesInTime">Dodaj aktivnost u raspored</a>
    <a href="searchComments">Komentari</a>
    <% } %>

    <% if (p != null) { %>
    <a style ="float: right" href="logout">Odjava</a>
    <a style ="float: right">Prijavljeni ste kao <%= p.getNickname() %></a>
    <% if (!(p instanceof Organizator)) { %>
    <a href="schedule">Podaci i komentiranje</a>
    <% } %>

    <% } else { %>
    <a href="signUpAnimator">Registracija za animatora</a>
    <a href="signUpParticipant">Registracija za sudionika</a>
    <a href="login">Prijava</a>
    <% } %>
</div>

<% if (p != null) { %>
<div id="timer" class="timer-div" style="text-align: center;
    font-size: 70px;
    margin-top: 20%;"></div>
<% } else { %>
<% ActivityInTime start = (ActivityInTime) request.getAttribute("campStart"); %>
<% ActivityInTime end = (ActivityInTime) request.getAttribute("campEnd"); %>

<% if (start!= null) { %>
<h2 style="color: white;font-size: 38px;">kamp pocinje: <%= start.getStart() %></h2>
<% } %>
<br>

<% if (end!= null) { %>
<h2 style="color: white;font-size: 38px;">kamp završava: <%= end.getStart() %></h2>
<% } %>

<div class="activity-div">
    <table class="container">
        <thead>
        <tr>
            <th><h1>Naziv</h1></th>
            <th><h1>Opis</h1></th>
            <th><h1>Trajanje</h1></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="act" items="${activity}">
            <tr>
                <td>${act.name}</td>
                <td>${act.activityDescription}</td>
                <td>${act.durationInMinutes}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<% } %>


<% if (p instanceof Particiant || p instanceof Animator) { %>

<div class="activity-div">
    <table class="container">
        <caption>AGENDA</caption>
        <thead>
        <tr>
            <th><h1>Naziv</h1></th>
            <th><h1>Pocetak</h1></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="act" items="${activities}">
            <tr>
                <td>${act.name}</td>
                <td>${act.start}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<% } %>
<% if (!(p instanceof Organizator)) { %>
<div style="margin-top: 10%; margin-left: 35%">
    <a href = "mailto: kamp.mlade.nade@gmail.com" style="font-size: 30px; color: white; margin: auto">kamp.mlade.nade@gmail.com</a>
</div>
<% } %>

</body>
</html>

<%@ page import="java.util.List" %>
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
            position: fixed;
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
    <link rel="stylesheet" href="schedule.css">
</head>
<body>
<div class="top-div">
    <a href="/" class="web-title">KAMP MLADE NADE</a>
</div>

<% Person p = (Person) session.getAttribute("user"); %>

<div class="navbar">
    <a href="logout" style="float: right">odjava</a>
    <a style="float: right">Prijavljeni ste kao <%= p.getNickname() %></a>
</div>

<div>
    <% if (p instanceof Particiant) { %>
    <h2 style="color: white">Vaši animatori</h2>
    <table class="container">
        <thead>
        <tr>
            <th><h1>Koricnicko ime</h1></th>
            <th><h1>Ime</h1></th>
            <th><h1>Prezime</h1></th>
            <th><h1>Email</h1></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="animator" items="${participantAnimators}">
            <tr>
                <td>${animator.getNickname()}</td>
                <td>${animator.getName()}</td>
                <td>${animator.getSurname()}</td>
                <td>${animator.getEmail()}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <hr>
    <% Group g = (Group) request.getAttribute("participantGroup"); %>
    <h2 style="color: white">Vaša grupa je:  <%= g.getName() %></h2>
    <table class="container">
        <thead>
        <tr>
            <th><h1>Korisnicko ime</h1></th>
            <th><h1>Ime</h1></th>
            <th><h1>Prezime</h1></th>
            <th><h1>Email</h1></th>
        </tr>
        </thead>
        <tbody>
        <% for (Particiant particiant : ((Particiant) p).getCampGroup().getParticiants()) { %>
        <tr>
            <td><%= particiant.getNickname() %></td>
            <td><%= particiant.getName() %></td>
            <td><%= particiant.getSurname() %></td>
            <td><%= particiant.getEmail() %></td>
        </tr>
        <% } %>
        </tbody>
    </table>
    <hr>
    <h2 style="color: white">Vaše aktivnosti</h2>
    <table class="container">
        <thead>
        <tr>
            <th><h1>Naziv aktivnosti</h1></th>
            <th><h1>Ocijenite aktivnost</h1></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="activity" items="${userActivities}">
            <tr>
                <td>${activity.getName()}</td>
                <td><a style="color: white; text-decoration: none;" href="/review?name=${activity.getName()}">Ocijenite aktivnost ${activity.getName()}</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

</div>
<% } else if(p instanceof Animator) { %>
<% List<Group> allGroups = (List<Group>) request.getAttribute("allGroups"); %>
<div>
    <% for (Group group : allGroups) { %>
    <h2 style="color: white"><%= group.getName() %></h2>
    <table class="container">
        <thead>
        <tr>
            <th><h1>Korisničko ime</h1></th>
            <th><h1>Ime</h1></th>
            <th><h1>Prezime</h1></th>
            <th><h1>Email</h1></th>
        </tr>
        </thead>
        <tbody>
        <% for (Particiant particiant : group.getParticiants()) { %>
        <tr>
            <td><%= particiant.getNickname() %></td>
            <td><%= particiant.getName() %></td>
            <td><%= particiant.getSurname() %></td>
            <td><%= particiant.getEmail() %></td>
        </tr>
        <% } %>
        </tbody>
    </table>
    <% } %>
    <h2 style="color: white">Animatori</h2>
    <table class="container">
        <thead>
        <tr>
            <th><h1>Korisničko ime</h1></th>
            <th><h1>Ime</h1></th>
            <th><h1>Prezime</h1></th>
            <th><h1>Email</h1></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="anim" items="${allAnimators}">
            <tr>
                <td>${anim.nickname}</td>
                <td>${anim.email}</td>
                <td>${anim.surname}</td>
                <td>${anim.email}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<h2 style="color: white">Vaše aktivnosti:</h2>
<table class="container">
    <thead>
    <tr>
        <th><h1>Naziv aktivnosti</h1></th>
        <th><h1>Ocijenite aktivnost</h1></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="activity" items="${userActivities}">
        <tr>
            <td>${activity.getName()}</td>
            <td><a style="color: white; text-decoration: none;" href="/review?name=${activity.getName()}">Ocijenite aktivnost ${activity.getName()}</a></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<% } %>
</body>
</html>

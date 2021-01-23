<%@ page import="java.util.List" %>
<%@ page import="fer.ppj.model.*" %>
<%@ page import="java.util.Map" %>
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
    <link rel="stylesheet" href="index1.css">
</head>
<body>

<div class="top-div">
    <a href="/" class="web-title">KAMP MLADE NADE</a>
</div>

<% Person p = (Person) session.getAttribute("user"); %>
<% Map<Activity, List<Comment>> comments = (Map<Activity, List<Comment>>) request.getAttribute("commentsByActivities"); %>
<% List<Comment> searchedComments = (List<Comment>) request.getAttribute("searchedComments"); %>

<div class="navbar">
    <a href="logout" style="float: right">odjava</a>
    <a style="float: right">Prijavljeni ste kao <%= p.getNickname() %></a>
</div>
</div>


<div class="login-box">
    <h2>Pretraživanje</h2>
    <div id="form-main">
        <div id="form-div">
            <form class="form" id="form1" action="/searchComments" method ="POST">
                <div class="user-box" style="margin-left: 30%">

                    <select id="category" title="category" name="category">
                        <option value="user">nadimku korisnika</option>
                        <option value="group">imenu grupe</option>
                        <option value="activity">imenu aktivnosti</option>
                    </select>
                </div>
                <label for="searchBy">Klučna riječ<label>
                    <input type="text" name="searchBy" required=""  id="searchBy" style="margin-top : 5%">

                    <button type="submit" class="round-button" style="margin-top: 5%; margin-left: 25%">Pretraži</button>
            </form>
        </div>
    </div>
</div>
<% if (searchedComments != null) { %>
<% if (searchedComments.size() != 0) { %>
<hr>
Rezultati pretrage:
<div class="activity-div">
    <table class="container">
        <thead>
        <tr>
            <th><h1>Korisnik</h1></th>
            <th><h1>Komentar</h1></th>
            <th><h1>Ocjena</h1></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="com" items="${searchedComments}">
            <tr>
                <td>${com.user.nickname}</td>
                <td>${com.text}</td>
                <td>${com.mark}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<% } else { %>
Nema takvih komentara
<% } %>
<% } %>

<hr>

<% for (Map.Entry<Activity, List<Comment>> e : comments.entrySet()) { %>
<h3 style="margin-left: 45%; margin-block-start: 0; color: white"><%= e.getKey().getName() %></h3>
<div class="activity-div">
    <table class="container">
        <thead>
        <tr>
            <th><h1>Korisnik</h1></th>
            <th><h1>Komentar</h1></th>
            <th><h1>Ocjena</h1></th>
        </tr>
        </thead>
        <tbody>
        <% for (Comment c : (List<Comment>) e.getValue()) { %>
        <tr>
            <td><%= c.getUser().getNickname() %></td>
            <td><%= c.getText() %></td>
            <td><%= c.getMark() %></td>
        </tr>
        <% } %>
        </tbody>
    </table>
</div>
<% } %>
<div>

</body>
</html>

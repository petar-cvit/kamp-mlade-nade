<%@ page import="fer.ppj.model.Person" %>
<%@ page import="fer.ppj.model.Organizator" %>
<%@ page import="fer.ppj.model.Group" %>
<%@ page import="java.util.List" %>
<%@ page import="fer.ppj.model.Particiant" %>
<%@ page import="java.util.Map" %>
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
    <link rel="stylesheet" href="timer.css">
</head>
<body>
<div class="top-div">
    <a href="/" class="web-title">KAMP MLADE NADE</a>
</div>

<% Map<String, Boolean> activities = (Map<String, Boolean>) request.getAttribute("activities"); %>
<% Person p = (Person) session.getAttribute("user"); %>
<h2 class="form-title">Postavi vrijeme</h2>
<div class="login-box">
    <% for (Map.Entry<String, Boolean> e : activities.entrySet()) { %>
    <% if (!e.getValue()) { %>
    <form action="/setTimer" method="POST">

        <div class="user-box" style="margin-left: 10%">
            <div class="vl"></div>
            <input type="datetime-local" id="timer" name="<%= e.getKey() %>">
            <label for="timer">
                <% switch (e.getKey()) {
                    case "campStart":
                        out.print("pocetak kampa");
                        break;
                    case "campEnd":
                        out.print("kraj kampa");
                        break;
                    case "animatorStart":
                        out.print("pocetak prijava animatora");
                        break;
                    case "animatorEnd":
                        out.print("kraj prijava animatora");
                        break;
                    case "participantStart":
                        out.print("pocetak prijava sudionika");
                        break;
                    case "participantEnd":
                        out.print("kraj prijava sudionika");
                        break;
                } %>
            </label>

        </div>

        <button type="submit" class="round-button" style="margin-left: 30%">Postavi</button>
    </form>
    <% } %>
    <% } %>

</div>
</body>
</html>

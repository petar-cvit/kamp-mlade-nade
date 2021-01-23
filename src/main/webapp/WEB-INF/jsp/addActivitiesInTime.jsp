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
    <link rel="stylesheet" href="addActivitiesInTime.css">
</head>
<body>

<% Person p = (Person) session.getAttribute("user"); %>

<div>
    <div class="top-div">
        <a href="/" class="web-title">KAMP MLADE NADE</a>
    </div>
    <% List<ActivityInTime> activitiesInTime = (List<ActivityInTime>) request.getAttribute("activitiesInTime"); %>
    <% List<Activity> activities = (List<Activity>) request.getAttribute("activities"); %>
    <% List<Group> groups = (List<Group>) request.getAttribute("groups"); %>
    <% List<Animator> animators = (List<Animator>) request.getAttribute("animators"); %>
    <% List<List<String>> missingActivities = (List<List<String>>) request.getAttribute("missingActivities"); //%>
    <% String error = (String) request.getAttribute("error");  //%>
    <% if (activitiesInTime.size() == 0) { %>
    <h1>Nema aktivnosti za prikaz</h1>
    <% } %>
</div>
<div class = "login-box">
    <h2>Dodaj aktivnost u raspored</h2>

    <% if(error != null) {  %>
        <h2 style="color: red"><%= error%></h2>
    <% }%>

    <form action="addActivitiesInTime" method="post">
        <div class="form-div">
            <label for="activityName" style="color: white">Ime aktivnosti: </label>
        <select id="activityName" title="ime aktivnosti" name="activityName">
            <% for (Activity activity : activities) { %>
            <option value="<%= activity.getName() %>"><%= activity.getName() %></option>
            <% } %>
        </select>
        </div>

        <div class="group-div">
            <div class="form-div">
                <h4 style="color: white">Grupe: </h4>
                <ul>
        <% for (Group group : groups) { %>
                    <li style="color: white"><input type="checkbox" name="selectedGroups" value="<%= group.getId() %>"><%= group.getName() %>
        <% } %>
                    </ul>
            </div>
        </div>

        <div class="animator-div">
            <div class="form-div">
                <h4 style="color: white">Animatori: </h4>
                <ul>
            <% for (Animator animator : animators) { %>
               <li style="color: white"><input type="checkbox" name="selectedAnimators" value="<%= animator.getNickname() %>"><%= animator.getNickname() %> <%= animator.getName() %> <%= animator.getSurname() %>
            <% } %>
                </ul>
            </div>
        </div>

        <div class="form-div">
            <label for="start" style="color: white">Pocetak: </label>
            <input type="datetime-local" id="start" name="start" value="2000-01-01" required="">
        </div>
        <button type="submit" class="round-button" style="margin-top: 5%; margin-left: 25%">Dodaj aktivnost</button>
        <% if(request.getAttribute("error")!=null){ %>
        <div class="animator-div">
            <div class="form-div">
                <% String pravilo =  (String) request.getAttribute("error"); %>
                <h4 style="color: white"> Aktivnost nije dodana zbog kršenja sljedećeg pravila - <%= pravilo %> </h4>
                <% } %>
            </div>
        </div>


    </form>
</div>

<% if(missingActivities != null) { %>
<div class="group-div">
    <div class="list-div">
        <h4 style="color: green; text-align: center">Grupe i aktivnosti koje nedostaju </h4>
        <ul>
            <% for (int i = 0, n = groups.size(); i < n; i++) { %>
            <li style="color: #FB667A">Grupa <%= groups.get(i).getId()%>:

                <ul>
                <% for(String activityName : missingActivities.get(i)) { %>
                    <li style="color: white"><%= activityName %><li>
                <% } %>
                </ul>
            </li>
            <% } %>
        </ul>
    </div>
</div>

<% } %>

</body>
</html>

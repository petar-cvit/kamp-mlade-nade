<%@ page import="fer.ppj.model.Person" %>
<%@ page import="fer.ppj.model.Organizator" %>
<%@ page import="fer.ppj.model.Group" %>
<%@ page import="java.util.List" %>
<%@ page import="fer.ppj.model.Particiant" %>
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
    <link rel="stylesheet" href="createGroups.css">
</head>
<body>


<% Person p = (Person) session.getAttribute("user"); %>


<div class="top-div">
    <a href="/" class="web-title" style="top: 10%">KAMP MLADE NADE</a>
</div>

<div>
    <% List<Group> groups = (List<Group>) request.getAttribute("groups"); %>
    <% if (groups.size() == 0) { %>
    <div class="login-box">
    <form action="/divideGroups" method="POST" autocomplete="off">
        <div>
        <label for="numberOfGroups"style="color: white">Broj grupa: </label>
        <input type="number" id="numberOfGroups" name="numberOfGroups" min="1">
        </div>
        <button type="submit" class="round-button"style="margin-top: 5%">Stvori Grupe</button>
    </form>
    </div>
</div>
<div>
    <% List<Particiant> particiants = (List<Particiant>) request.getAttribute("participants"); %>
    <div class="activity-div" style="margin-left: 20%; margin-top: 40%; position: absolute;">
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
            <% for (Particiant participant : particiants) { %>
            <tr>
                <td><%= participant.getNickname() %></td>
                <td><%= participant.getName() %></td>
                <td><%= participant.getSurname() %></td>
                <td><%= participant.getEmail() %></td>
            </tr>
            <% } %>
            </tbody>
        </table>
    </div>
</div>
    <% } else { %>
        <% for (Group g : groups) { %>
        <h3 style="margin-left: 45%; margin-block-start: 0; color: white"><%= g.getName() %></h3>
        <div class="groups-div">
            <table class="container">
                <thead>
                <tr>
                    <th><h1>Korisničko ime</h1></th>
                    <th><h1>Ime</h1></th>
                    <th><h1>Prezime</h1></th>
                    <th><h1>Email</h1></th>
                    <th><h1>Promjena grupe</h1></th>
                </tr>
                </thead>
                <tbody>
                <% for (Particiant participant : g.getParticiants()) { %>
                <tr>
                    <td><%= participant.getNickname() %></td>
                    <td><%= participant.getName() %></td>
                    <td><%= participant.getSurname() %></td>
                    <td><%= participant.getEmail() %></td>
                    <td>
                        <form class="form" id="form1" action="/changeGroup" method ="POST">
                            <select id="groupName" title="groupName" name="groupName">
                                <% for (Group group : groups) { %>
                                <option value="<%= group.getName() %>"><%= group.getName() %></option>
                                <% } %>
                            </select>

                            <input type="text" name="nickname" style="display: none" value="<%= participant.getNickname() %>" readonly>


                            <input type="submit" value="SUBMIT" id="button-blue" style="margin-top: 1%;margin-left: 2%;"/>
                                <div class="ease"></div>

                        </form>

                    </td>
                </tr>
                <% } %>
                </tbody>
            </table>
        </div>
<% } %>
<div>
    <% } %>
</div>
</body>
</html>

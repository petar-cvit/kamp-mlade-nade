<%@ page import="fer.ppj.model.Person" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">

<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="signUp.css">
    <title>Sign up</title>
</head>

<body>

<div class="top-div">
    <a href="/" class="web-title">KAMP MLADE NADE</a>
</div>

<% if (request.getAttribute("error") != null) { %>
<h3 style="color: red">
    Sva polja su obavezna
</h3>
<% } %>

<div class="login-box">
    <h2>Dodaj Aktivnost</h2>
    <form method="post" action="addActivity" class="center-form" autocomplete="off">
        <div class="user-box">
            <input type="text" id="name" name="name" required="">
            <label for="name">Ime</label>
        </div>
        <div class="user-box">
            <input type="text" id="desc" name="desc" required="">
            <label for="desc">Opis</label>
        </div>
        <div class="user-box">
            <input type="number" id="duration" name="duration" value="30" required="">
            <label for="duration">Trajanje</label>
        </div>

        <label class="container" for="ONE">Jedna grupa
            <input type="radio" id="ONE" name="activityType" value="ONE">
            <span class="checkmark"></span>
        </label>
        <label class="container" for="N">N grupa
            <input type="radio" id="N" name="activityType" value="N">
            <span class="checkmark"></span>
        </label>
        <label class="container" for="MAX_N">maksimalno N
            <input type="radio" id="MAX_N" name="activityType" value="MAX_N">
            <span class="checkmark"></span>
        </label>
        <label class="container" for="ALL">Sve grupe
            <input type="radio" id="ALL" name="activityType" value="ALL">
            <span class="checkmark"></span>
        </label>
        <div class="user-box">
            <input type="number" id="numberOfGroups" name="numberOfGroups" value="1" required="">
            <label for="numberOfGroups">Broj grupa</label>
        </div>

        <button type="submit" class="round-button">Stvori Aktivnost</button>
    </form>
</div>

</body>

</html>

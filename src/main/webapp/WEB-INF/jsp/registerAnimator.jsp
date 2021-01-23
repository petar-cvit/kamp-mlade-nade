<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" %>
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

<% if (request.getAttribute("validEmail") != null) { %>
<h3 style="color: red">
    Netočna email adresa
</h3>
<% } %>

<% if (request.getAttribute("disabled") != null) { %>
<h3 style="margin-left: 33%; color: #FB667A; font-size: 35px;" > Prijave trenutno nisu dostupne</h3>

<% } else { %>
<div class="login-box">
    <h2>Registracija</h2>
    <form method="post" action="signUpAnimator" class="center-form" autocomplete="off">
        <div class="user-box">
            <input type="text" id="name" name="name" required="">
            <label for="name">Ime</label>
        </div>
        <div class="user-box">
            <input type="text" id="surname" name="surname" required="">
            <label for="surname">Prezime</label>
        </div>
        <div class="user-box">
            <input type="text" id="email" name="email" required="">
            <label for="email">E-mail</label>
        </div>
        <div class="user-box">
            <input type="text" id="contact" name="contact" required="">
            <label for="contact">Kontakt</label>
        </div>
        <div class="user-box">
            <input type="date" id="birthday" name="birthday" value="2000-01-01" required="">
            <label for="birthday">Datum rođenja</label>
        </div>
        <div>
            <label for="motivation" name="motivation" style="color: white; font-size: 17px">Motivacijsko pismo</label>
            <textarea id="motivation" name="motivation"  style ="width: -webkit-fill-available; height: 7em;"></textarea>
        </div>
        <span></span>
        <span></span>
        <span></span>
        <span></span>
        <button type="submit" class="round-button">Registriraj se</button>
    </form>
</div>
<% } %>

</body>

</html>

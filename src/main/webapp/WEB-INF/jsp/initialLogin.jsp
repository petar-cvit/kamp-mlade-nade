<%@ page import="fer.ppj.model.Person" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<%@ taglib prefix="c"
           uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<head>
    <title>Home page</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="loginAnimator.css">
</head>
<body>
<div class="top-div">
    <a href="/" class="web-title">KAMP MLADE NADE</a>
</div>

<% Person p = (Person) session.getAttribute("user"); %>

<div>
    <%= p.getNickname() %>
    <%= p.getName() %>
    <%= p.getSurname() %>
</div>

<% if (request.getAttribute("error") != null) { %>
<h2 style="color: red">Lozinka mora biti ista.</h2>
<% } %>

<div class = "login-box" id="loginBox">

<form method="post" action="setPassword" class="center-form" autocomplete="off">
    <div class="user-box">
        <input type="password" id="password" name="password" required="">
        <label for="password" style="color: white">Lozinka</label>
    </div>

    <div class="user-box">
        <input type="password" id="confirmPassword" name="confirmPass" required="">
        <label for="confirmPassword" style="color: white">Potvrdi lozinku</label>
    </div>

    <button type="submit" class="round-button">Registriraj se</button>
</form>
</div>
</body>
</html>

<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

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

<% if (request.getAttribute("dberror") != null) { %>
<h3 style="color: red">
    Pokušajte s drugim korisničkim imenom
</h3>
<% } %>

<div class="login-box">
    <h2>Dodaj organizatora</h2>
    <form method="post" action="signUpOrganizer" class="center-form" autocomplete="off">
        <div class="user-box">
            <input type="text" id="username" name="username" required="">
            <label for="username">Korisničko ime:</label>
        </div>
        <div class="user-box">
            <input type="password" id="password" name="password" required="">
            <label for="password">Lozinka:</label>
        </div>
        <button type="submit" class="round-button">Dodaj</button>
    </form>
</div>

</body>

</html>

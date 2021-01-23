<%@ page import="fer.ppj.model.Person" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8" content="text/html;charset=UTF-8">
    <link rel="stylesheet" href="loginAnimator.css">
    <title>Login animator</title>
</head>
<body>

<div class="top-div">
    <a href="/" class="web-title">KAMP MLADE NADE</a>
</div>


<span></span>
<span></span>
<div class="login-box">
    <h2>Prijava</h2>
    <form onsubmit="login" method="post" autocomplete="off">
        <% if (request.getAttribute("invalidPassword") != null) { %>
        <label style="color: red">Kriva lozinka</label>
        <% } %>

        <% if (request.getAttribute("invalidUsername") != null) { %>
        <label style="color: red">Neispravno korisničko ime</label>
        <% } %>

        <% if (request.getAttribute("notAccepted") != null) { %>
        <label style="color: red">Korisnik nije prihvaćen</label>
        <% } %>

        <div class="user-box">
            <input type="text" id="name" name="username" required="">
            <label for="name">Korisničko ime</label>
        </div>

        <div class="user-box">
            <input type="password" id="password" name="password" required="">
            <label for="password">Lozinka</label>

        </div>
            <span></span>
            <span></span>
            <span></span>
            <span></span>
            <button type="submit" class="round-button">Prijavi se</button>
    </form>
</div>
</div>




</body>
</html>

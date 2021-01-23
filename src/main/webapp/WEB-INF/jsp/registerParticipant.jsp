<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8" %>
<html lang="en" xmlns:th="http://www.thymeleaf.org">

<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="signUp.css">
    <title>Sign up</title>
</head>
<script>
    function checkAge(){
        var checkBox = document.getElementById("ageCheck");
        var div = document.getElementById("hiddenDiv");

        if (checkBox.checked === true){
            div.style.display = "block";
        } else {
            div.style.display = "none";
        }
    }
</script>

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
    <form method="post" action="signUpParticipant" class="center-form" autocomplete="off">
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
        <div>
            <label for="ageCheck" style="color: white; margin-bottom: 5%;">Maloljetan/na</label>
            <input type="checkbox" name="underage" id="ageCheck" onclick="checkAge()">
        </div>
        <div class="user-box">
            <input type="date" id="birthday" name="birthday" value="2000-01-01" style="margin-top: 10%"  required="">
            <label for="birthday" style="margin-top: 10%">Datum rođenja</label>
        </div>
        <div class="user-box" id="hiddenDiv" style="display: none">
            <input type="text" name="custodian" id="parentContact">
            <label for="parentContact">Kontakt odgovorne osobe</label>
        </div>
        <div>
            <label for="motivation" style="color: white; font-size: 17px">Motivacijsko pismo</label>
            <textarea id="motivation" name="motivation"  style ="width: -webkit-fill-available; height: 7em;"></textarea>
        </div>

        <button type="submit" class="round-button">Registriraj se</button>

    </form>
</div>
<% } %>

</body>

</html>

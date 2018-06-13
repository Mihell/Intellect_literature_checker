<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html" pageEncoding="Cp1251"%>
<html>
<head>
    <title>result of checking</title>
    <style>
        body {
            padding: 110px;
            background-size: cover;
            font-size: x-large;
        <% if ((int)request.getAttribute("rating") > 50){%>
            background-image: url(resources/resultImage.jpg);
            color: maroon;
        <%} else{%>
            background-image: url(resources/resultImageBad.jpg);
            color:blue;
        <%}%>
        }

        form{
            position: absolute;
            bottom: 50px;
            left:90px;
        }

        input{
            cursor: pointer;
            color: navy;
            background-color:silver;
            border-radius: 20px;
        }

        input:hover{
            background-color:aqua;
        }
    </style>
</head>
<body>
<h1 align="center">THIS BOOK APPROPRIATE YOU FOR:</h1>
<h1 align="center"><%=request.getAttribute("rating")%> %</h1>
<form action="/upload" method="get" >
    <input type="submit" value="To main page">
</form>
</body>
</html>
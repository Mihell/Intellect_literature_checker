<%@ page import="java.util.ArrayList" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@ page language="java" contentType="text/html" pageEncoding="Cp1251" %>
<html>
<head>

  <title> Literature checker </title>
  <style>
    body {
      padding: 20px;
      background-image: url(resources/indexImage.jpg);
      background-size: cover;
      font-size: x-large;
      color: maroon;
    }

    h1 {
      color: blue;
    }

    input {
      cursor: pointer;
      color: navy;
    }

    input.but {
      background-color: silver;
      border-radius: 20px;
    }

    input.but:hover {background-color: aqua;}

    div {
      padding-top: 60px;
      padding-left: 250px;
    }

    p {padding-left: 310px;}
    a {
      position: absolute;
      right: 300px;
      bottom: 140px;
      color:green;
    }
    a:hover {color:blue}

  </style>
</head>
<body>

<h1 align="center">CHECK YOUR BOOK</h1>
<form action="/upload" method="post" enctype="multipart/form-data">
  Choose your favourite books:
  <input type="file" name="book">
  <input class="but" type="submit" value="upload book" onclick="alert('book added')">
</form>
<br>
<form action="/check" method="post" enctype="multipart/form-data">
  Choose unknown book for check:
  <input type="file" name="newBook">
  <input class="but" type="submit" value="Check book">
</form>
<div>
  Your favourite books:
</div>
<%
  if (request.getAttribute("books") != null) {
    for (String b : (ArrayList<String>) request.getAttribute("books")) {
%>
<p><%= b%>
</p>
<%
    }
  }
%>
<a href="/upload?clear=true">Clear list</a>
</form>
</body>
</html>

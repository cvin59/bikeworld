<%--
  Created by IntelliJ IDEA.
  User: asus
  Date: 10/4/2018
  Time: 1:25 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta title="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
<div class="header">
    <jsp:include page="header.jsp"/>
</div>
<div>
    <input id="eventId" value="${event.id}">
    <img id="imgAva" src="${event.imageUrl}" style="position: relative; display: block; height: 518px; width: 1349px"/>
    <input id="eventName" value="${event.title}">
</div>
</body>
</html>

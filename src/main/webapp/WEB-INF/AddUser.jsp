<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 02-May-18
  Time: 14:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add User</title>
</head>
<body>
<a href="/"><button>Գլխավոր</button></a><br>
<span style="color: red"><h3>${message}</h3></span>

<spring:form action="/add" modelAttribute="user" method="post">
    <spring:input path="name" placeholder="անուն"></spring:input><br>
    <spring:input path="country" placeholder="քաղաք" ></spring:input><br>
    <spring:input path="tel" placeholder="հեռախոս "></spring:input><br>
    <spring:button>ավելացնել</spring:button>

</spring:form>
</body>
</html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 02-May-18
  Time: 21:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>update</title>
</head>
<body>
<a href="/"><button>Գլխավոր</button></a><br>
<span style="color: red"><h3>${message}</h3></span>
<spring:form action="/up" modelAttribute="user" method="post">
    <spring:hidden path="id" value="${user.id}"></spring:hidden>
    <spring:input path="name" value="${user.name}" placeholder="անուն"></spring:input><br>
    <spring:input path="country" value="${user.country}" placeholder="քաղաք" ></spring:input><br>
    <spring:input path="tel" value="${user.tel}" placeholder=" հեռախոս"></spring:input><br>
    <spring:button>թարմացնել</spring:button>

</spring:form>
</body>
</html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 12-Sep-18
  Time: 10:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>change</title>
</head>
<body>
<a  href="/"><button style="border-radius: 200px;border-color: red">Գլխավոր</button></a>
<h3 style="color: red">${credit.user.name}&nbsp;${credit.user.country}&nbsp;${credit.user.tel}&nbsp; &nbsp; &nbsp;</h3>
<spring:form action="/updatePrice" modelAttribute="creditor" method="post">
    <spring:hidden path="id" value="${credit.id}"></spring:hidden>
    Նոր պարտք  <spring:input type="number" step="50" path="value" placeholder="partq"></spring:input><br>
    Նոր ամիս  <spring:input type="date"  path="date" placeholder="partq"></spring:input><br>
    <spring:button style="border-radius: 200px;border-color: red">թարմացնել</spring:button>
</spring:form>
</body>
</html>

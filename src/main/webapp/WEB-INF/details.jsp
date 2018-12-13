<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 02-May-18
  Time: 17:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>detail</title>
</head>
<body>
<a href="/"><button>Գլխավոր</button></a>

<h3 style="color: red">${user.name}&nbsp;${user.country}&nbsp;${user.tel}&nbsp; &nbsp; &nbsp;
    <a href="/update?id=${user.id}"><button style="border-radius: 200px;border-color: red">թարմացնել</button ></a>&nbsp; &nbsp; &nbsp;
    <a href="/deleteUser?id=${user.id}"><button style="border-radius: 200px;border-color: red"> ջնջել ցուցակից </button></a><br>${message}</h3>


<spring:form action="/addCredit" modelAttribute="creditor" method="post">
    <spring:hidden path="user" value="${user.id}"></spring:hidden>
    Նոր պարտք  <spring:input type="number" step="50" path="value" placeholder="partq"></spring:input>
    <spring:button style="border-radius: 200px;border-color: red">ավելացնել</spring:button>
</spring:form>
<span style="width: 280px;float: left;">
    <h2>պարտք</h2>
<ol>
    <li><h3 style="color: olive">ամիս/օր &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; գումար</h3></li>


    <c:forEach items="${user.credits}" var="one">
        <c:if test="${one.type=='NEW'}">

            <fmt:parseDate var="parsedEmpDate" pattern="yyyy-MM-dd"
                           value = "${one.date}" />

            <li><h3><fmt:formatDate dateStyle="short"  value="${parsedEmpDate}" pattern="MM-dd" type="date"></fmt:formatDate>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="/change?id=${one.id}"><button style="border-radius: 200px;border-color: red">-</button></a> ${one.value}
                <a href="/changeType?id=${one.id}"><button style="border-radius: 200px;border-color: red">+</button></a>
            </li>

        </c:if>
    </c:forEach>
    <%--<span style="color: red"><h3>GUMAR ${sum}</h3></span>--%>

    <li style="color: red"><h3>ընդհանուր=   <c:if test="${credit.size()==0}">
        պարտք  չունի
    </c:if> ${userSum}</h3></li>
</ol>
     </span>

<span  style="width: 5px;float: left;"><hr width="1" size="500"></span>

<span style="width: 280px;float: left;" >

<h2>Տված</h2>
<ol >
     <li><h3 style="color: olive">ամիս/օր &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; գումար</h3></li>
    <c:forEach items="${credit}" var="one">
        <c:if test="${one.type=='END'}">
            <fmt:parseDate var="parsedEmpDate" type="both" pattern="yyyy-MM-dd"
                           value = "${one.date}" />


            <li><h3><fmt:formatDate dateStyle="short" pattern="MM-dd" value="${parsedEmpDate}" type="date"></fmt:formatDate>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${one.value}
                <a href="/deletePrice?id=${one.id}"><button>ջնջել</button></a></li>
        </c:if>
    </c:forEach>
</ol>
    </span>

</body>
</html>

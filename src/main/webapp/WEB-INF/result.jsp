<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 03-May-18
  Time: 00:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>պարտք</title>
</head>
<body>
<%--<a href="/">--%>
    <%--<button style="border-radius: 200px;border-color: red">Գլխավոր</button>--%>
<a href="/allByMax">
    <button style="border-radius: 200px;border-color: red">Գլխավոր</button></a>
<%--</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="/admin">--%>
    <%--<button style="border-radius: 200px;border-color: red">Նոր</button>--%>
<%--</a>--%>
<%--<br>--%>
<%--<form action="/search">--%>
    <%--<input name="search" placeholder="փնտրել">--%>
    <%--<input style="border-radius: 200px;border-color: red" type="submit" value="փնտրել">--%>
<%--</form>--%>
<form action="/searchDtoByName">
    <input name="name" placeholder="փնտրել">
    <input STYLE="color: red;border-radius: 500px;border-color: red" type="submit" value="փնտրել">
</form>
<h2 style="color: red">${message}</h2>
<ul style="padding-left: 150px;">
    <c:forEach items="${allDtos}" var="one">
        <li style="width: 300px;float: left;padding-right: 20px; border-style: ridge; background-color: greenyellow;"><h4>${one.user.name}
            &nbsp;${one.user.country}&nbsp;&nbsp;&nbsp;${one.user.tel}&nbsp;<span style="color: red;float:  right">=${one.sum}</span></h4>
        </li>
    </c:forEach>
</ul>

<ul style="padding-left: 150px;">
    <c:forEach items="${userSumDto}" var="one">
        <li style="width: 300px;float: left;padding-right: 20px; border-style: ridge; background-color: greenyellow;"><h4>${one.user.name}
            &nbsp;${one.user.country}&nbsp;&nbsp;&nbsp;${one.user.tel}&nbsp;<span style="color: red;float:  right">=${one.sum}</span></h4>
                </li>
    </c:forEach>
</ul>


<%--<span style="width: 280px;float: left;padding: 60px">--%>
<%--<ul style="width: 400px;float: left;padding: 0px;">--%>
        <%--<c:forEach items="${allByMax}" var="one">--%>
            <%--<li style="border-style: ridge; background-color: pink"><h4>= ${one}</h4></li>--%>
        <%--</c:forEach>--%>
<%--</ul>--%>
<%--</span>--%>
<br>

<ol style="padding: 100px">
    <%--<li style="width: 400px"><h3 style="color: olive">անուն&nbsp;&nbsp;&nbsp;&nbsp;Հասցե</h3></li>--%>
    <h2 style="color: red">${mess}</h2>
    <c:forEach items="${allUsers}" var="one">

        <li style="width: 300px;float: left;border-style: ridge;background-color: pink">
            <h3>${one.name}&nbsp;${one.country}
                <a href="/credit?id=${one.id}">
                    <button style="border-color: red;border-radius: 200px">OK</button>
                </a>
        </li>

    </c:forEach>
    <%--</ol>--%>
    <%--<ol style="padding: 100px">--%>
    <%--<li style="width: 400px"><h3 style="color: olive">անուն&nbsp;&nbsp;&nbsp;&nbsp;Հասցե</h3></li>--%>

    <c:forEach items="${allByDate}" var="one">


        <li style="width: 300px;float: left;border-style: ridge;background-color: pink">
            <h3>${one.user.name}&nbsp;${one.user.country}
                <a href="/credit?id=${one.user.id}">
                    <button style="border-color: red;border-radius: 200px">OK</button>
                </a><br>${one.armDate}&nbsp;&nbsp;${one.value}
        </li>

    </c:forEach>
</ol>



</body>
</html>

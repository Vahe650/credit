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
    <title>result</title>
</head>
<body>
<%--<a href="/">--%>
    <%--<button style="border-radius: 200px;border-color: red">Գլխավոր</button>--%>
<%--</a>&nbsp;&nbsp;&nbsp;&nbsp;<a href="/admin">--%>
    <%--<button style="border-radius: 200px;border-color: red">Նոր</button>--%>
<%--</a>--%>
<%--<br>--%>
<%--<form action="/search">--%>
    <%--<input name="search" placeholder="փնտրել">--%>
    <%--<input style="border-radius: 200px;border-color: red" type="submit" value="փնտրել">--%>
<%--</form>--%>
<span style="width: 280px;float: left;padding: 60px">
<ol style="width: 400px;float: left;padding: 0px;">
    <c:forEach items="${allUsersByMax}" var="one">
        <li style="border-style: ridge; background-color: pink;"><h4>${one.user.name}
    &nbsp;${one.user.country}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${one.user.tel}</h4>
                </li>
    </c:forEach>
</ol>
    </span>

<span style="width: 280px;float: left;padding: 60px">
<ul style="width: 400px;float: left;padding: 0px;">
        <c:forEach items="${allByMax}" var="one">
            <li style="border-style: ridge; background-color: pink"><h4>= ${one}</h4></li>
        </c:forEach>
</ul>
</span>
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

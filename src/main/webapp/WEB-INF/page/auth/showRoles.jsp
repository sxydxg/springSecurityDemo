<%--
  Created by IntelliJ IDEA.
  User: ASUS
  Date: 2019/9/21
  Time: 10:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>显示你所有的权限</title>
</head>
<body>
   用户名：${username}    <br/>
   拥有的角色：<br/>
    <c:forEach items="${roles}" var="r" varStatus="vs">
        ${vs.index}---> ${r}<br>
    </c:forEach>
</body>
</html>

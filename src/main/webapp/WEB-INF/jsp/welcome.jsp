<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/head.jsp"%>
<div class="right">
    <img class="wColck" src="${pageContext.request.contextPath }/statics/images/clock.jpg" alt=""/>
    <div class="wFont">
        <h2>${sessionScope.user.username}</h2>
        <p>欢迎来到超市订单管理系统!</p>
        <a href="${pageContext.request.contextPath}/user/delete.do">执行删除功能</a>
    </div>
</div>
</section>
<%@include file="/WEB-INF/jsp/common/foot.jsp" %>

<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>欢迎页面</title>
</head>
<body>欢迎
    <div>
     ${sessionScope.user.username}
    </div>
登录！
</body>
</html>--%>

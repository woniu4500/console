<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<f:setBundle basename="lang/globalMessages" scope="application"/>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
	<title>弘昊金融管理系统</title>
	<link rel="icon" href="r/img/icon/credit.ico">
	<link rel="stylesheet" href="r/css/main.min.css">
</head>
<body class="random-background">
	<div class="container">
		<form class="justify" id="f" name='f' action='<c:url value="/j_spring_security_check" />' method='post'>
			<h3 class="text-center logo"><img src="r/img/logo-hh.png" /></h3>
			
			<div class="form-group form-group-lg">
				<label for="username" class="sr-only">账号</label>
				<input name='j_username' class="form-control" id="username" class="name fl" placeholder="请输入账号" autofocus />
			</div>
			
			<div class="form-group form-group-lg">
				<label for="pwd" class="sr-only">密码</label>
				<input name='j_password' class="form-control" id="pwd" type="password" placeholder="请输入密码" autocomplete="off" />
			</div>
			
			<div class="form-group form-group-lg">
				<button class="btn btn-lg btn-primary btn-block" type="submit">登录</button>
			</div>
			
			<div class="form-group form-group-lg">
				<p class="messages"><c:if test="${not empty param.login_error}">${SPRING_SECURITY_LAST_EXCEPTION.message}</c:if></p>
			</div>
		</form>
	</div>
	
	<script src="r/js/underscore.min.js"></script>
	<script src="r/plugins/jquery/jquery-1.11.0.min.js"></script>
	<script src="r/js/random-background.js"></script>
  <script src="r/js/justify.js"></script>
</body>
</html>
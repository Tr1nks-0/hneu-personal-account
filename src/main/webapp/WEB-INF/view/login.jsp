<%@ page language="java" contentType="text/html; charset=utf8"
		 pageEncoding="utf8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
	<link rel="stylesheet"
		  href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
	<link rel="stylesheet"
		  href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap-theme.min.css">
</head>
<body>
<div class="container">
	<div id="loginbox" style="margin-top: 50px;"
		 class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
		<div class="panel panel-info">
			<div class="panel-heading">
				<div class="panel-title">
					<spring:message code="label.login" />
				</div>
			</div>
			<div style="padding-top: 30px" class="panel-body">
				<c:if test="${error}">
					<div id="login-alert" class="alert alert-danger col-sm-12">
						<spring:message code="error.login" />
					</div>
				</c:if>
				<form name='loginForm'
					  action="<c:url value='/j_spring_security_check' />" method='POST'>
					<div style="margin-bottom: 25px" class="input-group">
							<span class="input-group-addon"><i
									class="glyphicon glyphicon-user"></i></span> <input id="login-username"
																						type="text" class="form-control" name="username" value=""
																						placeholder="username or email">
					</div>
					<div style="margin-bottom: 25px" class="input-group">
							<span class="input-group-addon"><i
									class="glyphicon glyphicon-lock"></i></span> <input id="login-password"
																						type="password" class="form-control" name="password"
																						placeholder="password">
					</div>
					<div style="margin-top: 10px" class="form-group">
						<button type="submit" style="width:100%" class="btn btn-success"><spring:message code="label.login"/></button>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</body>
</html>
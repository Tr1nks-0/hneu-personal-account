<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<html lang="ua">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" type="text/css" href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">
    <link rel="stylesheet" type="text/css" href="/resources/fonts/font-awesome.min.css">
    <link rel="stylesheet" type="text/css" href="/resources/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="/resources/css/form-elements.css">
    <link rel="stylesheet" type="text/css" href="/resources/css/style.css">
</head>
<body>
<div class="top-content mask">
    <div class="inner-bg">
        <div class="container">
            <div class="row">
                <div class="hidden-xs col-sm-8 col-sm-offset-2 text">
                    <h1>
                        <strong><spring:message code="form.label.hneu"/></strong>
                        <spring:message code="form.label.student.profile"/>
                    </h1>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-sm-offset-3 form-box">
                    <div class="form-top">
                        <div class="form-top-left">
                            <h3><spring:message code="form.login.label.login"/></h3>
                            <p><spring:message code="form.login.label.login.description"/></p>
                        </div>
                    </div>
                    <c:if test="${error}">
                        <div id="login-alert" class="alert alert-danger col-sm-12">
                            <spring:message code="form.login.error.login"/>
                        </div>
                        <br class="visible-lg"/>
                    </c:if>
                    <div class="form-bottom">
                        <form name='loginForm' action="<c:url value='/j_spring_security_check' />" method='POST'>
                            <div class="form-group">
                                <input id="login-username" type="text" class="form-username form-control"
                                       id="form-username" name="username" value=""
                                       placeholder="<spring:message code="form.login.label.email"/>"/>
                            </div>
                            <div class="form-group">
                                <input type="password" class="form-password form-control"
                                       id="form-password" name="password"
                                       placeholder="<spring:message code="form.login.label.password"/>">
                            </div>
                            <button type="submit" style="width:100%" class="btn btn-success">
                                <spring:message code="form.login.label.login"/>
                            </button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="/resources/js/jquery-1.12.0.min.js"></script>
<script type="text/javascript" src="/resources/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/resources/js/jquery.backstretch.min.js"></script>
<script type="text/javascript" src="/resources/js/scripts.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        $.backstretch("/resources/img/back.jpg");
    });
</script>
</body>
</html>
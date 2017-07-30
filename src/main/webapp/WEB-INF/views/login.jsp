<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<html lang="ua">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">

    <tags:css/>

</head>
<body class="gradient-background">
<div class="parent">
    <div class="child">
        <div class="container">
            <div id="login-form" class="row">
                <div class="col-xs-10 col-xs-offset-1 col-sm-6 col-sm-offset-3 col-md-4 col-md-offset-4">
                    <div class="panel panel-default box-shadow">
                        <div class="panel-heading">
                            <div class="panel-title">
                                <img class="center logo" src="/resources/img/logo-md-black.svg"/>
                            </div>
                        </div>
                        <c:if test="${error}">
                            <div class="alert alert-danger no-border no-radius col-md-12">
                                <spring:message code="form.login.error.login"/>
                            </div>
                        </c:if>
                        <div class="panel-body">
                            <spring:message code="form.login.label.login.description"/>
                        </div>
                        <div class="panel-footer">
                            <a href="/connect/google" class="btn btn-block btn-social btn-google">
                                <span class="fa fa-google"></span> Sign in with Google
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<tags:js/>

</body>
</html>
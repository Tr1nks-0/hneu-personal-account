<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<html lang="ua">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
    <%@ include file="jspf/cssTag.jspf" %>
    <link rel="stylesheet" href="/resources/css/custom/login.css">
</head>
<body>
<div class="top-content mask">
    <div class="inner-bg">

        <div class="container">
            <div class="row">
                <div class="span14 columns offset2">

                </div>
            </div>
        </div>
        <div class="container">
            <div class="row">
                <div class="hidden-xs col-sm-8 col-sm-offset-2 text text-center">
                    <h1>
                        <strong><spring:message code="form.label.hneu"/></strong>
                        <span class="hidden-sm hidden-xs">|</span>
                        <spring:message code="form.label.student.profile"/>
                    </h1>
                </div>
            </div>
            <div class="row">
                <div class="col-xs-10 col-xs-offset-1 col-sm-4 col-sm-offset-4 form-box">
                    <div class="box box-primary">
                        <div class="box-header with-border">
                            <h3 class="box-title"><spring:message code="form.login.label.login"/></h3><br>
                        </div>
                        <c:if test="${error}">
                            <div id="login-alert" class="alert no-border alert-danger col-sm-12">
                                <spring:message code="form.login.error.login"/>
                            </div>
                            <br class="visible-lg"/>
                        </c:if>
                            <div class="box-body text-center">
                                <spring:message code="form.login.label.login.description"/>
                            </div>
                            <div class="box-footer">
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
<%@ include file="jspf/jsTag.jspf" %>
<script type="text/javascript" src="/resources/js/jquery/jquery.backstretch.min.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        $.backstretch("/resources/img/back.jpg");
    });
</script>
</body>
</html>
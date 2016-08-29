<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<html lang="ua">
<head>
    <%@ include file="jspf/cssTag.jspf" %>
    <link rel="stylesheet" href="/resources/css/custom/login.css">
</head>
<body>
<div class="top-content mask">
    <div class="inner-bg">
        <div class="container">
            <div class="row">
                <div class="hidden-xs col-sm-8 col-sm-offset-2 text text-center">
                    <h1>
                        <strong><spring:message code="form.label.hneu"/></strong>
                        <spring:message code="form.label.student.profile"/>
                    </h1>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-sm-offset-3 form-box">
                    <div class="box box-primary">
                        <div class="box-header with-border">
                            <h3 class="box-title"><spring:message code="form.login.label.login"/></h3><br>
                            <spring:message code="form.login.label.login.description"/>
                        </div>
                        <c:if test="${error}">
                            <div id="login-alert" class="alert no-border alert-danger col-sm-12">
                                <spring:message code="form.login.error.login"/>
                            </div>
                            <br class="visible-lg"/>
                        </c:if>
                        <form name='loginForm' action="<c:url value='/j_spring_security_check' />" method='POST'>
                            <div class="box-body">
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
                            </div>
                            <div class="box-footer">
                                <button type="submit" style="width:100%" class="btn btn-primary">
                                    <spring:message code="form.login.label.login"/>
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script type="text/javascript" src="/resources/js/jquery/jquery-2.2.3.min.js"></script>
<script type="text/javascript" src="/resources/js/bootstrap/bootstrap.min.js"></script>
<script type="text/javascript" src="/resources/js/jquery/jquery.backstretch.min.js"></script>
<script type="text/javascript">
    $(document).ready(function () {
        $.backstretch("/resources/img/back.jpg");
    });
</script>
</body>
</html>
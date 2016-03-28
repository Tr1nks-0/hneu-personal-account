<%@ page language="java" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page session="false" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link rel="stylesheet" type="text/css" href="/resources/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="/resources/css/bootstrap-theme.min.css"/>
    <link rel="stylesheet" type="text/css" href="/resources/css/form-elements.css">
    <link rel="stylesheet" type="text/css" href="/resources/css/style.css">
    <link rel="stylesheet" type="text/css" href="/resources/css/main.css">
    <link rel="stylesheet" type="text/css" href="/resources/css/jquery.ui.theme.css">
    <script type="text/javascript" src="/resources/js/jquery-1.12.0.min.js"></script>
    <script src="http://code.jquery.com/ui/1.10.2/jquery-ui.js"></script>
    <script type="text/javascript" src="/resources/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="/resources/js/jquery.backstretch.min.js"></script>
    <script type="text/javascript" src="/resources/js/scripts.js"></script>
</head>
<body>
<div class="top-content">
    <div class="inner-bg">
        <div class="container">
            <div class="row">
                <div class=" col-xs-10 col-sm-10 col-md-6 col-lg-6 form-box col-xs-offset-1 col-sm-offset-1 col-md-offset-3 col-lg-offset-3">
                    <div class="form-top">
                        <div class="form-top-left">
                            <h3>Реєстрація</h3>
                            <p>Fill in the form below to get instant access:</p>
                        </div>
                        <div class="form-top-right">
                            <i class="fa fa-pencil"></i>
                        </div>
                    </div>
                    <div class="form-bottom">
                        <form:form action="/register" method='POST' commandName="registerForm">
                            <div class="form-group">
                                <label class="sr-only" for="form-profile-id">First name</label>
                                <form:input path="profileId" id="profile-id" type="text" name="form-profile-id"
                                            placeholder="First name..." class="form-first-name form-control"/>
                            </div>
                            <div class="form-group">
                                <label class="sr-only" for="form-email">Last name</label>
                                <form:input path="email" id="email" type="text" name="form-email"
                                            placeholder="Last name..."
                                            class="form-last-name form-control"/>
                            </div>
                            <div class="form-group">
                                <label class="sr-only" for="form-password">Email</label>
                                <form:input path="password" id="password" type="text" name="form-password"
                                            placeholder="Email..."
                                            class="form-email form-control"/>
                            </div>
                            <div class="form-group">
                                <label class="sr-only" for="form-confirmed-password">Email</label>
                                <form:input path="confirmedPassword" id="confirmed-password" type="text"
                                            name="form-confirmed-password"
                                            placeholder="Email..." class="form-email form-control"/>
                            </div>
                            <button type="submit" class="btn">Give it to me!</button>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(document).ready(function () {
        $(function () {
            $("#profile-id").autocomplete({
                source: function (request, response) {
                    $.ajax({
                        url: "/register/profiles",
                        type: "GET",
                        data: {term: request.term},
                        contentType: "text/plain; charset=utf-8",
                        dataType: "json",
                        success: function (data) {
                            response($.map(data, function (v) {
                                return {
                                    label: v.tagName,
                                    value: v.id
                                };
                            }));
                        }
                    });
                }
            });
        });
    });
</script>

</body>
</html>
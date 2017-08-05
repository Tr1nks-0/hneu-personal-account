<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" session="false" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<html>
<head lang="ua">
    <title>Smth went wrong...</title>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">

    <tags:css/>
</head>
<body>
<div class="container">
    <div class="row">
        <div class="col-md-12">
            <div class="error-template">
                <h1>Oops!</h1>
                <div class="error-details">
                    На жаль, сталася помилка, спробуйте будь-ласка пізніше!
                </div>
                <div class="error-actions">
                    <a href="/account" class="btn btn-primary btn-lg">
                        <i class="fa fa-home"></i>
                        На головну
                    </a>
                </div>
            </div>
        </div>
    </div>
</div>
</body>

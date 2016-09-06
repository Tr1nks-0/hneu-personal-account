<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ include file="../jspf/header.jspf" %>

<style>
    .hold-transition {
        overflow: hidden;
    }
</style>

<div class="content-wrapper">
    <section class="content-header" style="padding: 0px">
        <iframe class="schedule-frame" src="http://services.ksue.edu.ua:8081/schedule/schedule?group=${groupId}">
        </iframe>
    </section>
</div>

<%@ include file="../jspf/jsTag.jspf" %>


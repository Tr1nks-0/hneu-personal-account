<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="jspf/header.jspf" %>

<style>
    /* Hide all unneeded frames*/
    div {
        height: 0px;
    }
</style>

<iframe class="schedule-frame" src="http://services.ksue.edu.ua:8081/schedule/schedule?group=${groupId}"/>
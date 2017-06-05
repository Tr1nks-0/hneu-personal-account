<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.5.0/css/font-awesome.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
<link rel="stylesheet" href="http://fonts.googleapis.com/css?family=Roboto:400,100,300,500">
<link rel="stylesheet" href="/resources/css/bootstrap/bootstrap.min.css">

<c:set var="profiles">
    <spring:eval expression="@environment.getProperty('spring.profiles.active')"/>
</c:set>
<c:choose>
    <c:when test="${fn:contains(profiles, 'production')}">
        <link rel="stylesheet" href="/resources/css/main.min.css">
    </c:when>
    <c:otherwise>
        <link rel="stylesheet" href="/resources/css/main.css">
    </c:otherwise>
</c:choose>


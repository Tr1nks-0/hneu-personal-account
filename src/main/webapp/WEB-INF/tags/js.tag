<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script src="/resources/js/jquery/jquery-2.2.3.min.js"></script>
<script src="https://code.jquery.com/ui/1.11.4/jquery-ui.min.js"></script>
<script src="/resources/js/bootstrap/bootstrap.min.js"></script>
<script src="/resources/js/template/app.min.js"></script>
<script src="/resources/js/template/demo.js"></script>

<c:set var="profiles">
    <spring:eval expression="@environment.getProperty('spring.profiles.active')"/>
</c:set>
<c:choose>
    <c:when test="${fn:contains(profiles, 'production')}">
        <script src="/resources/js/main.min.js"></script>
    </c:when>
    <c:otherwise>
        <script src="/resources/js/main.js"></script>
    </c:otherwise>
</c:choose>
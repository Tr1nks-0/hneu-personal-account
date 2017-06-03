<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ attribute name="success" required="false" type="java.lang.String"%>

<c:if test="${not empty success}">
    <div class="alert alert-success alert-dismissible"><spring:message code="${success}"/></div>
</c:if>

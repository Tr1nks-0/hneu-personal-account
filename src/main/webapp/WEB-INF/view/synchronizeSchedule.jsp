<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="jspf/header.jspf" %>
<%@ include file="jspf/management-header.jspf" %>

<div class="bs-callout bs-callout-info">
    <h4><spring:message code="form.label.management.synchronize.schedule.header"/></h4>
    <p><spring:message code="form.label.management.synchronize.schedule.message"/></p>
    <a class="btn btn-success btn-large" href="/management/downloadGroups"><spring:message
            code="form.label.management.synchronize.schedule.integration"/></a>
</div>

<%@ include file="jspf/management-footer.jspf" %>
<%@ include file="jspf/footer.jspf" %>

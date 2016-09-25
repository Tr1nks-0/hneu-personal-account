<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="../jspf/management-header.jspf" %>

<div class="content-wrapper">
    <section class="content-header">
        <h1>
            <spring:message code="form.label.management.schedule.synchronized.header"/>
        </h1>
        <div class="content">
            <div class="box box-primary">
                <div class="box-header with-border">
                    <spring:message code="form.label.management.schedule.synchronized.message"/>
                </div>
            </div>
        </div>
    </section>
</div>

<%@ include file="../jspf/management-footer.jspf" %>
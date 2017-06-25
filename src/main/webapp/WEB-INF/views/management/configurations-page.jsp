<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="info" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="togglz" uri="http://togglz.org/taglib"%>

<%@ include file="../jspf/management-header.jspf" %>

<div class="content-wrapper">
    <div class="content">

        <ol class="breadcrumb panel panel-default">
            <li><i class="fa fa-home"></i></li>
            <li class="active"><spring:message code="form.label.management.configurations"/></li>
        </ol>

        <info:error error="${error}"/>
        <info:success success="${success}"/>

        <div class="row">
            <div class="col-md-7">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <div class="row">
                            <div class="col-md-12">
                                <spring:message code="form.label.management.configurations.admins"/>
                            </div>
                        </div>
                    </div>
                    <div class="panel-body">
                        <p><spring:message code="form.label.management.configurations.admins.add"/></p>
                        <form action="/management/configs/admins" method="post">
                            <div class="form-group">
                                <label for="email" class="control-label"><spring:message code="form.label.student.email"/></label>
                                <input id="email" name="email" class="form-control" required="required" type="email"/>
                            </div>
                            <input type = "submit" value = "<spring:message code="btn.add"/>" class="btn btn-sm btn-success float-right"/>
                        </form>
                    </div>
                    <ul class="list-group">
                        <c:forEach items="${admins}" var="admin">
                            <li class="list-group-item">
                                <div class="row">
                                    <div class="col-md-12">
                                        ${admin.id}
                                    </div>
                                </div>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>

            <div class="col-md-5">
                <form method="post" action="/management/configs/email-sending">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <div class="row">
                                <div class="col-md-12">
                                    <spring:message code="form.label.management.configurations.emails"/>
                                    <button type="submit" class="btn btn-xs btn-success pull-right"><spring:message code="btn.save"/></button>
                                </div>
                            </div>
                        </div>
                        <div class="panel-body">
                            <togglz:feature name="SEND_EMAIL_AFTER_PROFILE_CREATION" var="sendEmailAfterProfileCreation"/>
                            <div class="checkbox">
                                <label>
                                    <input name="sendEmailAfterProfileCreation" type="checkbox" <c:if test="${sendEmailAfterProfileCreation}">checked</c:if> data-toggle="toggle">
                                    <spring:message code="form.label.management.configurations.emails.after.profile.creation"/>
                                </label>
                            </div>

                            <togglz:feature name="SEND_EMAIL_AFTER_PROFILE_MODIFICATION" var="sendEmailAfterImportingMarks"/>
                            <div class="checkbox">
                                <label>
                                    <input name="sendEmailAfterImportingMarks" type="checkbox" <c:if test="${sendEmailAfterImportingMarks}">checked</c:if> data-toggle="toggle">
                                    <spring:message code="form.label.management.configurations.emails.after.importing.marks"/>
                                </label>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<%@ include file="../jspf/management-footer.jspf" %>

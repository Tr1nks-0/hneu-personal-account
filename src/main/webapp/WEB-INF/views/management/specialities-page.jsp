<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="info" tagdir="/WEB-INF/tags" %>

<%@ include file="../jspf/management-header.jspf" %>

<div class="content-wrapper">
    <div class="content">

        <ol class="breadcrumb panel panel-default">
            <li><i class="fa fa-home"></i></li>
            <li><a href="/management/faculties"><spring:message code="form.label.management.faculties"/></a></li>
            <li class="active"><spring:message code="form.label.management.specialities"/></li>
        </ol>

        <form:form modelAttribute="speciality" action="/management/specialities" method="post">

            <info:error error="${error}"/>
            <info:success success="${success}"/>
            <form:errors path="*" cssClass="alert alert-danger alert-dismissible" element="div" />

            <div class="panel panel-default">
                <div class="panel-heading">
                    <div class="row">
                        <div class="col-md-12 panel-title">
                            <spring:message code="form.label.management.specialities.add"/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label for="faculty" class="control-label"><spring:message code="form.label.student.faculty"/></label>
                                <form:select path="faculty" items="${faculties}" itemLabel="name" itemValue="id" cssClass="form-control"/>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="panel-body">
                    <div class="form-group">
                        <label for="name" class="control-label"><spring:message code="form.label.student.speciality"/></label>
                        <form:input path="name" cssClass="form-control" required="required"/>
                    </div>
                    <input type = "submit" value = "<spring:message code="btn.save"/>" class="btn btn-success float-right"/>
                </div>
            </div>
        </form:form>

        <div class="panel panel-default">
            <div class="panel-heading">
                <div class="row">
                    <div class="col-md-12 panel-title">
                        <spring:message code="form.label.management.specialities"/>
                    </div>
                </div>
            </div>
            <div class="panel-body specialities-management-panel">
                <c:choose>
                    <c:when test="${not empty specialities}">
                        <table class="table no-margin">
                            <thead>
                                <tr>
                                    <th><spring:message code="form.label.name"/></th>
                                    <th><spring:message code="form.label.student.faculty"/></th>
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${specialities}" var="speciality">
                                    <tr>
                                        <td>${speciality.name}</td>
                                        <td>${speciality.faculty.name}</td>
                                        <td>
                                            <button data-speciality="${speciality.id}" class="delete-speciality btn btn-danger btn-xs pull-right">
                                                <i class="fa fa-remove"></i>
                                            </button>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </c:when>
                    <c:otherwise>
                        <spring:message code="items.not.found"/>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</div>

<%@ include file="../jspf/management-footer.jspf" %>
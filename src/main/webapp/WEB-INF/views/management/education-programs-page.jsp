<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="info" tagdir="/WEB-INF/tags" %>

<%@ include file="../jspf/management-header.jspf" %>

<div class="content-wrapper">
    <div class="content">

        <ol class="breadcrumb panel panel-default">
            <li><i class="fa fa-home"></i></li>
            <li><a href="/management/faculties"><spring:message code="form.label.management.faculties"/></a></li>
            <li><a href="/management/specialities"><spring:message code="form.label.management.specialities"/></a></li>
            <li class="active"><spring:message code="form.label.management.education.programs"/></li>
        </ol>

        <form:form modelAttribute="educationProgram" action="/management/education-programs" method="post">

            <info:error error="${error}"/>
            <info:success success="${success}"/>
            <form:errors path="*" cssClass="alert alert-danger alert-dismissible" element="div" />

            <div class="panel panel-default">
                <div class="panel-heading">
                    <div class="row">
                        <div class="col-md-12 panel-title">
                            <spring:message code="form.label.management.education.programs.add"/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="form-group">
                                <label for="faculty" class="control-label"><spring:message code="form.label.student.faculty"/></label>
                                <select id="faculty"  name="faculty" class="form-control">
                                    <c:forEach var="faculty" items="${faculties}">
                                        <option value="${faculty.id}" <c:if test="${faculty.id eq selectedFaculty.id}">selected="selected"</c:if></option> ${faculty.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="col-md-12">
                            <div class="form-group">
                                <label for="speciality" class="control-label"><spring:message code="form.label.student.speciality"/></label>
                                <form:select path="speciality" items="${specialities}" itemLabel="name" itemValue="id" cssClass="form-control"/>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="panel-body">
                    <div class="form-group">
                        <label for="name" class="control-label"><spring:message code="form.label.student.educationProgram"/></label>
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
                        <spring:message code="form.label.management.education.programs"/>
                    </div>
                </div>
            </div>
            <div class="panel-body education-programs-management-panel">
                <c:choose>
                    <c:when test="${not empty educationPrograms}">
                        <table class="table no-margin">
                            <thead>
                                <tr>
                                    <th><spring:message code="form.label.name"/></th>
                                    <th><spring:message code="form.label.student.speciality"/></th>
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${educationPrograms}" var="educationProgram">
                                    <tr>
                                        <td>${educationProgram.name}</td>
                                        <td>${educationProgram.speciality.name}</td>
                                        <td>
                                            <button  education-program-id="${educationProgram.id}" class="delete-education-program btn btn-danger btn-xs pull-right">
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
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="info" tagdir="/WEB-INF/tags" %>

<%@ include file="../jspf/management-header.jspf" %>

<div class="content-wrapper">
    <div class="content">

        <ol class="breadcrumb panel panel-default">
            <li><i class="fa fa-home"></i></li>
            <li class="active"><spring:message code="form.label.management.groups"/></li>
        </ol>

        <form:form modelAttribute="group" action="/management/groups" method="post">

            <info:error error="${error}"/>
            <info:success success="${success}"/>
            <form:errors path="*" cssClass="alert alert-danger alert-dismissible" element="div" />

            <div class="panel panel-default">
                <div class="panel-heading">
                    <div class="row">
                        <div class="col-md-12 panel-title">
                            <spring:message code="form.label.management.groups.add"/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6 form-group">
                            <label for="faculty" class="control-label"><spring:message code="form.label.student.faculty"/></label>
                            <select id="faculty"  name="faculty" class="form-control">
                                <c:forEach var="faculty" items="${faculties}">
                                    <option value="${faculty.id}" <c:if test="${faculty.id eq selectedFaculty.id}">selected="selected"</c:if></option> ${faculty.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-md-6 form-group">
                            <label for="speciality" class="control-label"><spring:message code="form.label.student.speciality"/></label>
                            <form:select path="speciality" items="${specialities}" itemLabel="name" itemValue="id" cssClass="form-control"/>
                        </div>
                        <div class="col-md-12 form-group">
                            <label for="educationProgram" class="control-label"><spring:message code="form.label.student.educationProgram"/></label>
                            <form:select path="educationProgram"  cssClass="form-control">
                                <form:option value="">&nbsp;</form:option>
                                <form:options items="${educationPrograms}" itemLabel="name" itemValue="id"/>
                            </form:select>
                        </div>
                    </div>
                </div>

                <div class="panel-body">
                    <div class="col-md-6 form-group">
                        <label for="name" class="control-label"><spring:message code="form.label.name"/></label>
                        <form:input path="name" cssClass="form-control" required="required"/>
                    </div>
                    <div class="col-md-6 form-group">
                        <label for="id" class="control-label"><spring:message code="form.label.code"/></label>
                        <form:input path="id" cssClass="form-control" type="number" required="required"/>
                    </div>
                    <div class="col-md-12">
                        <input type = "submit" value = "<spring:message code="btn.save"/>" class="btn btn-success float-right"/>
                    </div>
                </div>
            </div>
        </form:form>

        <div class="panel panel-default">
            <div class="panel-heading">
                <div class="row">
                    <div class="col-md-12">
                        <spring:message code="form.label.management.groups"/>
                    </div>
                </div>
            </div>
            <div class="panel-body disciplines-management-panel">
                <c:choose>
                    <c:when test="${not empty groups}">
                        <table class="table no-margin">
                            <thead>
                                <tr>
                                    <th><spring:message code="form.label.code"/></th>
                                    <th><spring:message code="form.label.name"/></th>
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${groups}" var="group">
                                    <tr>
                                        <td><a href="/management/students?groupId=${group.id}">${group.id}</a></td>
                                        <td>${group.name}</td>
                                        <td>
                                            <button  group-id="${group.id}" class="delete-groups btn btn-danger btn-xs pull-right">
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

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ include file="../jspf/management-header.jspf" %>

<div class="content-wrapper">
    <div class="content">
        <div class="box box-primary">
            <form:form modelAttribute="student" action="/management/students/${student.id}" method="post">
                <div class="box-header with-border">
                    <div class="pull-left"><h4>${student.email}</h4></div>
                    <div class="pull-right">
                        <input type = "submit" value = "Submit" class="btn btn-success"/>
                    </div>
                </div>
                <div class="box-body">

                    <c:if test="${not empty success}">
                        <div class="col-md-12">
                            <div class="alert alert-success alert-dismissible">
                                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                                <i class="icon fa fa-check"></i> ${success}
                            </div>
                        </div>
                    </c:if>

                    <table class="table table-responsive">
                        <tr>
                            <td class="col-md-2">
                                <label for="name" class="control-label"><spring:message code="form.label.student.name"/></label>
                            </td>
                            <td class="col-md-5">
                               <form:input path="name" cssClass="form-control"/>
                            </td>
                            <td class="col-md-5" rowspan="4" colspan=2>

                            </td>
                        </tr>
                        <tr>
                            <td class="col-md-2">
                                <label for="surname" class="control-label"><spring:message code="form.label.student.surname"/></label>
                            </td>
                            <td class="col-md-5">
                                <form:input path="surname" cssClass="form-control"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="col-md-2">
                                <label for="passportNumber" class="control-label"><spring:message code="form.label.student.passportNumber"/></label>
                            </td>
                            <td class="col-md-5">
                                <form:input path="passportNumber" cssClass="form-control" type="number"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="col-md-2">
                                 <label for="contactInfo" class="control-label"><spring:message code="form.label.student.contacts"/></label>
                            </td>
                            <td class="col-md-5">
                                <form:input path="contactInfo" cssClass="form-control"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="col-md-2">
                                <label for="faculty" class="control-label"><spring:message code="form.label.student.faculty"/></label>
                            </td>
                            <td class="col-md-5">
                                <form:input path="faculty" cssClass="form-control"/>
                            </td>
                            <td class="col-md-2">
                                 <label for="incomeYear" class="control-label"><spring:message code="form.label.student.incomeYear"/></label>
                            </td>
                            <td class="col-md-3">
                                <form:input path="incomeYear" cssClass="form-control" type="number"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="col-md-2">
                                <label for="speciality" class="control-label"><spring:message code="form.label.student.speciality"/></label>
                            </td>
                            <td class="col-md-5">
                                <form:input path="speciality" cssClass="form-control"/>
                            </td>
                            <td class="col-md-2">
                                 <label for="studentGroup.name" class="control-label"><spring:message code="form.label.student.studentGroup"/></label>
                            </td>
                            <td class="col-md-3">
                                <form:input path="studentGroup.name" cssClass="form-control"/>
                            </td>
                        </tr>
                        <c:if test="${not empty student.educationProgram}">
                            <tr>
                                <td class="col-md-2">
                                    <label for="educationProgram" class="control-label"><spring:message code="form.label.student.educationProgram"/></label>
                                </td>
                                <td class="col-md-5" colspan=3>
                                    <form:input path="educationProgram" cssClass="form-control"/>
                                </td>
                            </tr>
                        </c:if>
                    </table>

                    <div class="col-md-12">
                        <c:forEach items="${student.courses}" var="course" varStatus="i">
                            <div class="col-md-12">
                                <span class="label label-info">${course.label}</span>
                            </div>
                            <c:forEach items="${course.semesters}" var="semester" varStatus="j">
                                <div class="col-md-12">
                                    <div class="label label-success">${semester.label}</div>
                                </div>
                                <div class="col-md-12">
                                    <table class="table no-margin">
                                        <c:forEach items="${semester.disciplines}" var="discipline" varStatus="k">
                                            <tr>
                                                <td class="col-md-6"><form:input path="courses[${i.index}].semesters[${j.index}].disciplines[${k.index}].label" cssClass="form-control"/></td>
                                                <td class="col-md-1"><form:input path="courses[${i.index}].semesters[${j.index}].disciplines[${k.index}].credits" cssClass="form-control" type="number"/></td>
                                                <td class="col-md-2"><form:input path="courses[${i.index}].semesters[${j.index}].disciplines[${k.index}].controlForm" cssClass="form-control"/></td>
                                                <td class="col-md-2"><form:select path="courses[${i.index}].semesters[${j.index}].disciplines[${k.index}].type" items="${disciplineTypes}" cssClass="form-control"/>
                                                <td class="col-md-1"><form:input path="courses[${i.index}].semesters[${j.index}].disciplines[${k.index}].mark" cssClass="form-control" type="number"/></td>
                                            </tr>
                                            <form:hidden path="courses[${i.index}].semesters[${j.index}].disciplines[${k.index}].id"/>
                                        </c:forEach>
                                    </table>
                                </div>
                                <form:hidden path="courses[${i.index}].semesters[${j.index}].id"/>
                                <form:hidden path="courses[${i.index}].semesters[${j.index}].total"/>
                                <form:hidden path="courses[${i.index}].semesters[${j.index}].label"/>
                            </c:forEach>
                            <form:hidden path="courses[${i.index}].id"/>
                            <form:hidden path="courses[${i.index}].label"/>
                        </c:forEach>
                    </div>

                    <form:hidden path="average"/>
                    <form:hidden path="rate"/>
                    <form:hidden path="photo"/>
                    <form:hidden path="email"/>
                    <form:hidden path="studentGroup.id"/>
                </div>
            </form:form>
        </div>
    </div>
</div>

<%@ include file="../jspf/management-footer.jspf" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ include file="../jspf/management-header.jspf" %>

<style>
    .form-control-button {
        text-align: center;
        font-size: 20px;
        height: 31px;
        margin-top: 6px;
    }
</style>

<div class="content-wrapper">
    <div class="content">
        <div class="box box-primary">
            <form:form modelAttribute="student" action="/management/students/${student.id}" method="post">
                <div class="box-header with-border">
                    <div class="pull-left"><h4>${student.email}</h4></div>
                    <div class="pull-right">
                        <a class="btn btn-danger" href="/management/students/${student.id}/remove"><spring:message code="btn.remove"/></a>
                        <input type = "submit" value = "<spring:message code="btn.save"/>" class="btn btn-success"/>
                    </div>
                </div>
                <div class="box-body">

                    <c:if test="${not empty error}">
                        <div class="col-md-12">
                            <div class="alert alert-error alert-dismissible"><spring:message code="${error}"/></div>
                        </div>
                    </c:if>

                    <c:if test="${not empty success}">
                        <div class="col-md-12">
                            <div class="alert alert-success alert-dismissible"><spring:message code="${success}"/></div>
                        </div>
                    </c:if>

                    <form:errors path="*" cssClass="alert alert-danger alert-dismissible" element="div" />

                    <table class="table table-responsive">
                        <tr>
                            <td class="col-md-2">
                                <label for="name" class="control-label"><spring:message code="form.label.student.name"/></label>
                            </td>
                            <td class="col-md-5">
                                <form:input path="name" cssClass="form-control"/>
                            </td>
                            <td class="col-md-5 center" rowspan="4" colspan="2">
                                <img src="/students/${student.id}/photo" class="img-thumbnail" style="width: 150px">
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
                                <form:select path="faculty" items="${faculties}" cssClass="form-control" itemLabel="name" itemValue="id" disabled="true"/>
                            </td>
                            <td class="col-md-2">
                                <label for="incomeYear" class="control-label"><spring:message code="form.label.student.incomeYear"/></label>
                            </td>
                            <td class="col-md-3">
                                <form:input path="incomeYear" cssClass="form-control" type="number" disabled="true"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="col-md-2">
                                <label for="speciality" class="control-label"><spring:message code="form.label.student.speciality"/></label>
                            </td>
                            <td class="col-md-5">
                                <form:select path="speciality" items="${specialities}" cssClass="form-control" itemLabel="name" itemValue="id" disabled="true"/>
                            </td>
                            <td class="col-md-2">
                                <label for="group" class="control-label"><spring:message code="form.label.student.group"/></label>
                            </td>
                            <td class="col-md-3">
                                <form:select path="group" items="${groups}" cssClass="form-control" itemLabel="name" itemValue="id" disabled="true"/>
                            </td>
                        </tr>
                        <tr>
                            <td class="col-md-2">
                                <label for="educationProgram" class="control-label"><spring:message code="form.label.student.educationProgram"/></label>
                            </td>
                            <td class="col-md-5" colspan=3>
                                <form:select path="educationProgram" cssClass="form-control" disabled="true">
                                    <form:option value=""/>
                                    <form:options items="${educationPrograms}" itemLabel="name" itemValue="id"/>
                                </form:select>
                            </td>
                        </tr>
                    </table>

                    <div class="col-md-12">
                        <div class="box-body">
                            <div class="table-responsive">
                                <table class="table no-margin">
                                    <c:set var="course" value="0"/>
                                    <c:set var="semester" value="0"/>
                                    <c:forEach items="${student.disciplineMarks}" var="disciplineMarks" varStatus="i">
                                        <c:if test="${disciplineMarks.discipline.course ne course}">
                                            <c:set var="course" value="${disciplineMarks.discipline.course}"/>
                                            <tr>
                                                <td colspan="5"><span class="label label-primary">Курс №${course}</span></td>
                                            </tr>
                                        </c:if>
                                        <c:if test="${disciplineMarks.discipline.semester ne semester}">
                                            <c:set var="semester" value="${disciplineMarks.discipline.semester}"/>
                                            <tr>
                                                <td colspan="5"><span class="label label-default">СЕМЕСТР №${semester}</span></td>
                                            </tr>
                                        </c:if>
                                        <tr>
                                            <td class="col-md-6"><form:input path="disciplineMarks[${i.index}].discipline.label" cssClass="form-control" disabled="true"/></td>
                                            <td class="col-md-1"><form:input path="disciplineMarks[${i.index}].discipline.credits" cssClass="form-control" type="number" disabled="true"/></td>
                                            <td class="col-md-2"><form:select path="disciplineMarks[${i.index}].discipline.controlForm" items="${disciplineFormControls}" itemLabel="name" cssClass="form-control" disabled="true"/>
                                            <td class="col-md-2"><form:select path="disciplineMarks[${i.index}].discipline.type" items="${disciplineTypes}" itemLabel="name" cssClass="form-control" disabled="true"/>
                                            <td class="col-md-1"><form:input path="disciplineMarks[${i.index}].mark" cssClass="form-control" type="number"/></td>
                                            <form:hidden path="disciplineMarks[${i.index}].id"/>
                                            <form:hidden path="disciplineMarks[${i.index}].discipline.id"/>
                                            <form:hidden path="disciplineMarks[${i.index}].discipline.speciality"/>
                                            <form:hidden path="disciplineMarks[${i.index}].discipline.educationProgram"/>
                                            <form:hidden path="disciplineMarks[${i.index}].discipline.course"/>
                                            <form:hidden path="disciplineMarks[${i.index}].discipline.semester"/>
                                            <form:hidden path="disciplineMarks[${i.index}].discipline.credits"/>
                                            <form:hidden path="disciplineMarks[${i.index}].discipline.controlForm"/>
                                            <form:hidden path="disciplineMarks[${i.index}].discipline.label"/>
                                            <form:hidden path="disciplineMarks[${i.index}].discipline.type"/>
                                        </tr>
                                    </c:forEach>

                                </table>
                            </div>
                        </div>
                    </div>
                    <form:hidden path="incomeYear"/>
                    <form:hidden path="faculty"/>
                    <form:hidden path="speciality"/>
                    <form:hidden path="educationProgram"/>
                    <form:hidden path="average"/>
                    <form:hidden path="rate"/>
                    <form:hidden path="photo"/>
                    <form:hidden path="email"/>
                    <form:hidden path="group"/>
                </div>
            </form:form>
        </div>
    </div>
</div>

<%@ include file="../jspf/management-footer.jspf" %>

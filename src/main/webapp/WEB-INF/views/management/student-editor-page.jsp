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

        <ol class="breadcrumb panel panel-default">
            <li><i class="fa fa-home"></i></li>
            <li><a href="/management/students?groupId=${student.group.id}">${student.group.name}</a></li>
            <li class="active">${student.surname} ${student.name}</li>
        </ol>

        <form:form modelAttribute="student" action="/management/students/${student.id}" method="post">

            <div class="panel panel-default">
                <div class="panel-heading">
                    <div class="row">
                        <div class="col-md-12 panel-title">
                                ${student.surname} ${student.name}
                            <div class="pull-right">
                                <button class="btn btn-xs btn-danger" href="/management/students/${student.id}/delete">
                                    <i class="fa fa-remove"></i>
                                </button>
                                <button class="btn btn-xs btn-success" href="/management/students/${student.id}/delete">
                                    <i class="fa fa-save"></i>
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="panel-body">

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

                    <div class="row">
                        <div class="col-md-2 col-sm-3 col-xs-12 text-center">
                            <img src="/students/${student.id}/photo" class="img-thumbnail" style="max-height: 126px">
                        </div>
                        <div class="col-md-10 col-sm-9 col-xs-12">
                            <table class="table no-margin">
                                <tr>
                                    <td><label class="control-label"><spring:message code="form.label.student.email"/></label></td>
                                    <td>${student.email}</td>
                                </tr>
                                <tr>
                                    <td><label class="control-label"><spring:message code="form.label.student.name"/></label></td>
                                    <td>${student.name}</td>
                                </tr>
                                <tr>
                                    <td><label class="control-label"><spring:message code="form.label.student.surname"/></label></td>
                                    <td>${student.surname}</td>
                                </tr>
                            </table>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12">
                            <table class="table table-no-margin">
                                <tr>
                                    <td class="col-md-3"><label class="control-label"><spring:message code="form.label.student.passportNumber"/></label></td>
                                    <td>${student.passportNumber}</td>
                                </tr>
                                <tr>
                                    <td><label class="control-label"><spring:message code="form.label.student.faculty"/></label></td>
                                    <td>${student.faculty.name}</td>
                                </tr>
                                <tr>
                                    <td><label class="control-label"><spring:message code="form.label.student.speciality"/></label></td>
                                    <td>${student.speciality.name}</td>
                                </tr>
                                <tr>
                                    <td><label class="control-label"><spring:message code="form.label.student.group"/></label></td>
                                    <td>${student.group.name}</td>
                                </tr>
                                <c:if test="${not empty student.educationProgram}">
                                    <tr>
                                        <td><label class="control-label"><spring:message code="form.label.student.educationProgram"/></label></td>
                                        <td>${student.educationProgram.name}</td>
                                    </tr>
                                </c:if>
                                <tr>
                                    <td><label for="contactInfo" class="control-label"><spring:message code="form.label.student.contacts"/></label></td>
                                    <td><form:input path="contactInfo" cssClass="form-control"/></td>
                                </tr>
                            </table>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-md-12">
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
                </div>
            </div>

            <form:hidden path="id"/>
            <form:hidden path="email"/>
            <form:hidden path="name"/>
            <form:hidden path="surname"/>
            <form:hidden path="passportNumber"/>
            <form:hidden path="incomeYear"/>
            <form:hidden path="faculty"/>
            <form:hidden path="speciality"/>
            <form:hidden path="educationProgram"/>
            <form:hidden path="photo"/>
            <form:hidden path="group"/>
        </form:form>
    </div>
</div>
<%@ include file="../jspf/management-footer.jspf" %>

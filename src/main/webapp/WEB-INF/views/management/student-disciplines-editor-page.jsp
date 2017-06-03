<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="info" tagdir="/WEB-INF/tags" %>

<%@ include file="../jspf/management-header.jspf" %>

<div class="content-wrapper">
    <div class="content">

        <ol class="breadcrumb panel panel-default">
            <li><i class="fa fa-home"></i></li>
            <li><a href="/management/students?groupId=${student.group.id}">${student.group.name}</a></li>
            <li><a href="/management/students/${student.id}">${student.surname} ${student.name}</a></li>
            <li class="active"><spring:message code="form.label.management.disciplines"/></li>
        </ol>

        <form:form modelAttribute="disciplineMark" action="/management/students/${student.id}/disciplines" method="post" data-student="${student.id}">

            <info:error error="${error}"/>
            <info:success success="${success}"/>
            <form:errors path="*" cssClass="alert alert-danger alert-dismissible" element="div" />

            <div class="panel panel-default">
                <div class="panel-heading">
                    <div class="row">
                        <div class="col-md-2 col-xs-4 orm-group">
                            <label for="course" class="control-label"><spring:message code="form.label.student.profile.course"/></label>
                            <select id="course" class="form-control" type="number">
                                <c:forEach items="${courses}" var="course">
                                    <option value="${course}" <c:if test="${selectedCourse eq course}">selected</c:if>>${course}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-md-2 col-xs-4 form-group">
                            <label for="semester" class="control-label"><spring:message code="form.label.student.profile.semester"/></label>
                            <select id="semester" class="form-control" type="number">
                                <option value="1" <c:if test="${selectedSemester eq 1}">selected</c:if>>1</option>
                                <option value="2" <c:if test="${selectedSemester eq 2}">selected</c:if>>2</option>
                            </select>
                        </div>
                    </div>
                </div>

                <div class="panel-body">
                    <label for="discipline" class="control-label"><spring:message code="form.label.management.disciplines"/></label>
                    <div class="input-group">
                        <form:select path="discipline" cssClass="selectpicker selectpicker-left-from-control form-control">
                            <c:forEach items="${disciplines}" var="discipline">
                                <form:option value="${discipline.id}" label="${discipline.type.name} - ${discipline.label}"/>
                            </c:forEach>
                        </form:select>
                        <div class="input-group-btn">
                            <input type = "submit" class="btn btn-success float-right"
                                   <c:if test="${empty disciplineMark.discipline}">value="<spring:message code="btn.add"/>"</c:if>
                                   <c:if test="${not empty disciplineMark.discipline}">value="<spring:message code="btn.save"/>"</c:if>
                                   <c:if test="${empty disciplines}">disabled="true"</c:if>/>
                        </div>
                    </div>
                    <form:hidden path="id"/>
                    <form:hidden path="discipline"/>
                    <form:hidden path="mark"/>
                </div>
            </div>
        </form:form>

        <div class="panel panel-default">
            <div class="panel-heading">
                <spring:message code="form.label.management.disciplines"/>
            </div>
            <div id="student-disciplines" class="panel-body">
                <c:choose>
                    <c:when test="${not empty marks}">
                        <table class="table table-hover no-margin">
                            <thead>
                                <tr>
                                    <th><spring:message code="form.label.name"/></th>
                                    <th class="center hidden-xs"><spring:message code="form.label.discipline.credits"/></th>
                                    <th class="center hidden-xs"><spring:message code="form.label.discipline.control.form"/></th>
                                    <th class="center hidden-xs"><spring:message code="form.label.discipline.type"/></th>
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${marks}" var="mark">
                                    <tr>
                                        <td>
                                            <c:if test="${mark.discipline.type ne 'REGULAR'}">
                                                <a href="/management/students/${student.id}/disciplines/${mark.id}">${mark.discipline.label}</a>
                                            </c:if>
                                            <c:if test="${mark.discipline.type eq 'REGULAR'}">
                                                ${mark.discipline.label}
                                            </c:if>
                                        </td>
                                        <td class="center hidden-xs">${mark.discipline.credits}</td>
                                        <td class="center hidden-xs">${mark.discipline.controlForm.name}</td>
                                        <td class="center hidden-xs">${mark.discipline.type.name}</td>
                                        <td>
                                            <button class="btn btn-danger btn-xs delete-student-discipline"
                                                    <c:if test="${mark.discipline.type eq 'REGULAR'}">disabled</c:if>
                                                    data-discipline="${mark.id}">
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


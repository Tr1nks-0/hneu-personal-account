<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<%@ include file="../jspf/header.jspf" %>

<div class="content-wrapper">
    <div class="content">

        <ol class="breadcrumb panel panel-default">
            <li><a href="/account"><i class="fa fa-home"></i></a></li>
            <li class="active"><spring:message code="form.label.student.profile.head.second"/></li>
        </ol>

        <div class="panel panel-default">
            <div class="panel-body">
                <div class="student-info col-md-6 col-sm-12 col-xs-12">
                    <table class="table-sm no-margin no-border">
                        <tr>
                            <td rowspan="3"><i class="info-label ion-ios-book-outline"></i></td>
                            <td><spring:message code="form.label.student.faculty"/> <c:out value="${profile.faculty.name}"/></td>
                        </tr>
                        <tr>
                            <td><spring:message code="form.label.student.incomeYear"/> <c:out value="${profile.incomeYear}"/></td>
                        </tr>
                        <tr>
                            <td><spring:message code="form.label.student.group"/> <c:out value="${profile.group.name}"/></td>
                        </tr>
                    </table>
                </div>
                <c:if test="${not empty profile.contactInfo}">
                    <div class="student-info col-md-6 col-sm-12 col-xs-12">
                        <table class="table-sm no-margin no-border">
                            <tr>
                                <td rowspan="2"><i class="info-label ion ion-ios-telephone-outline"></i></td>
                                <td><spring:message code="form.label.student.contacts"/></td>
                            </tr>
                            <tr>
                                <td>
                                    <c:forEach items="${profile.contactInfo}" varStatus="i" var="contact">
                                        <div><c:out value="${contact}"/></div>
                                    </c:forEach>
                                </td>
                            </tr>
                        </table>
                    </div>
                </c:if>
            </div>
        </div>

        <c:forEach items="${courses}" var="course">
            <c:set var="showCourse" value="true"/>
            <c:forEach begin="1" end="2" var="semester">
                <c:set var="showSemester" value="true"/>
                <c:set var="showEndSemester" value="false"/>
                <c:forEach items="${profile.disciplineMarks}" var="disciplineMark" varStatus="i">
                    <c:if test="${disciplineMark.discipline.course == course and disciplineMark.discipline.semester == semester}">
                        <c:if test="${showSemester}">
                            <div class="panel panel-default">
                                <div class="panel-heading">
                                    <c:if test="${showCourse}">
                                        <span class="label label-primary">Курс №${course}</span>
                                        <c:set var="showCourse" value="false"/>
                                    </c:if>
                                    <span class="label label-default">СЕМЕСТР №${semester}</span>
                                    <span class="pull-right show-marks" data-toggle="collapse" data-target="#marks${course}${semester}">
                                        <i class="fa fa-chevron-left"></i>
                                    </span>
                                </div>
                                <div class="panel-body collapse" id="marks${course}${semester}">
                                    <div class="table-responsive">
                                        <table class="table no-margin">
                                            <thead>
                                            <tr>
                                                <th><spring:message code="form.label.student.profile.table.tab1"/></th>
                                                <th class="center hidden-xs"><spring:message code="form.label.student.profile.table.tab2"/></th>
                                                <th class="center hidden-xs"><spring:message code="form.label.student.profile.table.tab3"/></th>
                                                <th class="center"><spring:message code="form.label.student.profile.table.tab4"/></th>
                                            </tr>
                                            </thead>
                            <c:set var="showSemester" value="false"/>
                            <c:set var="showEndSemester" value="true"/>
                        </c:if>
                                            <tr>
                                                <td class="col-md-6"><span>${disciplineMark.discipline.label}</span></td>
                                                <td class="col-md-2 center hidden-xs">${disciplineMark.discipline.credits}</td>
                                                <td class="col-md-2 center hidden-xs">${disciplineMark.discipline.controlForm.name}</td>
                                                <td class="col-md-2 center"><tags:mark mark="${disciplineMark.mark}"/></td>
                                            </tr>
                    </c:if>
                </c:forEach>
                <c:if test="${showEndSemester}">
                                        </table>
                                    </div>
                                </div>
                            </div>
                </c:if>
            </c:forEach>
        </c:forEach>
    </div>
</div>

<%@ include file="../jspf/footer.jspf" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>

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

        <c:set var="course" value="0"/>
        <c:set var="semester" value="0"/>
        <c:forEach items="${profile.disciplineMarks}" var="disciplineMark" varStatus="i">
            <c:if test="${disciplineMark.discipline.course ne course or disciplineMark.discipline.semester ne semester}">
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <div class="row">
                            <div class="col-md-12">
                                <c:if test="${disciplineMark.discipline.course ne course}">
                                    <c:set var="course" value="${disciplineMark.discipline.course}"/>
                                </c:if>
                                <span class="label label-primary">Курс №${course}</span>
                                <c:if test="${disciplineMark.discipline.semester ne semester}">
                                    <c:set var="semester" value="${disciplineMark.discipline.semester}"/>
                                    <span class="label label-default">СЕМЕСТР №${semester}</span>
                                </c:if>
                                <a class="pull-right" data-toggle="collapse" href="#disciplines${i.index}">
                                    <i class="fa fa-minus"></i>
                                </a>
                            </div>
                        </div>
                    </div>
                    <div class="panel-body collapse in" id="disciplines${i.index}">
                        <table class="table no-margin">
                            <thead>
                                <tr>
                                    <th><spring:message code="form.label.student.profile.table.tab1"/></th>
                                    <th class="center hidden-xs"><spring:message code="form.label.student.profile.table.tab2"/></th>
                                    <th class="center hidden-xs"><spring:message code="form.label.student.profile.table.tab3"/></th>
                                    <th class="center"><spring:message code="form.label.student.profile.table.tab4"/></th>
                                </tr>
                            </thead>
                            <tbody>
            </c:if>
                                <tr>
                                    <td><c:out value="${disciplineMark.discipline.label}"/></td>
                                    <td class="center hidden-xs"><c:out value="${disciplineMark.discipline.credits}"/></td>
                                    <td class="center hidden-xs"><c:out value="${disciplineMark.discipline.controlForm.name}"/></td>
                                    <td class="center">
                                        <c:choose>
                                            <c:when test="${disciplineMark.mark ge '90' or disciplineMark.mark eq 'з' }">
                                                <button class="btn btn-success disabled">
                                                    <c:out value="${disciplineMark.mark}"/>
                                                </button>
                                            </c:when>
                                            <c:when test="${disciplineMark.mark ge '74' and disciplineMark.mark lt '90' }">
                                                <button class="btn btn-default disabled">
                                                    <c:out value="${disciplineMark.mark}"/>
                                                </button>
                                            </c:when>
                                            <c:when test="${disciplineMark.mark ge '60' and disciplineMark.mark lt '74' }">
                                                <button class="btn btn-warning disabled">
                                                    <c:out value="${disciplineMark.mark}"/>
                                                </button>
                                            </c:when>
                                            <c:when test="${(disciplineMark.mark ge '0' and disciplineMark.mark lt '60') or disciplineMark.mark eq 'н'}">
                                                <button class="btn btn-danger disabled">
                                                    <c:out value="${disciplineMark.mark}"/>
                                                </button>
                                            </c:when>
                                        </c:choose>
                                    </td>
                                </tr>
            <c:if test="${profile.disciplineMarks.size() > i.index
                and (profile.disciplineMarks[i.index + 1].discipline.course ne course or profile.disciplineMarks[i.index + 1].discipline.semester ne semester)}">
                            </tbody>
                        </table>
                    </div>
                </div>
            </c:if>
        </c:forEach>
    </div>
</div>

<%@ include file="../jspf/footer.jspf" %>


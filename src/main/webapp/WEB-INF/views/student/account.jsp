<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<%@ include file="../jspf/header.jspf" %>

<div class="content-wrapper">
    <section class="content-header">
        <h1>
            <spring:message code="form.label.student.profile.head"/>
            <small><spring:message code="form.label.student.profile.head.second"/></small>
        </h1>
    </section>
    <section class="content">
        <div class="content">
            <div class="box box-info">
                <div class="box-body">
                    <div class="col-md-6 col-sm-12 col-xs-12">
                        <span class="info-box-icon bg-white"><i class="ion-ios-book-outline"></i></span>
                        <div class="info-box-content">
                        <span class="info-box-text">
                            <spring:message code="form.label.student.faculty"/> <c:out value="${profile.faculty.name}"/>
                        </span>
                            <span class="info-box-text">
                            <spring:message code="form.label.student.incomeYear"/> <c:out value="${profile.incomeYear}"/>
                        </span>
                            <span class="info-box-text">
                            <spring:message code="form.label.student.studentGroup"/> <c:out value="${profile.studentGroup.name}"/>
                        </span>
                        </div>
                    </div>
                    <div class="col-md-6 col-sm-12 col-xs-12">
                        <span class="info-box-icon bg-white"><i class="ion ion-ios-telephone-outline"></i></span>
                        <div class="info-box-content">
                            <span class="info-box-text">
                                <spring:message code="form.label.student.contacts"/>
                            </span>
                            <div>
                                <c:forEach items="${profile.contactInfo}" varStatus="i" var="contact">
                                    <span class="info-box-number info-box-cropped">
                                        <small><c:out value="${contact}"/></small>
                                    </span>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <c:set var="course" value="0"/>
            <c:set var="semester" value="0"/>
            <c:forEach items="${profile.disciplineMarks}" var="disciplineMark" varStatus="i">
                <c:if test="${disciplineMark.discipline.course ne course or disciplineMark.discipline.semester ne semester}">
                    <div class="box box-info">
                        <div class="box-header with-border">
                            <div>
                                <c:if test="${disciplineMark.discipline.course ne course}">
                                    <c:set var="course" value="${disciplineMark.discipline.course}"/>
                                </c:if>
                                <span class="label label-primary">Курс №${course}</span>
                                <c:if test="${disciplineMark.discipline.semester ne semester}">
                                    <c:set var="semester" value="${disciplineMark.discipline.semester}"/>
                                    <span class="label label-default">СЕМЕСТР №${semester}</span>
                                </c:if>
                            </div>
                            <div class="box-tools pull-right">
                                <button type="button" class="btn btn-box-tool" data-widget="collapse">
                                    <i class="fa fa-minus"></i>
                                </button>
                            </div>
                        </div>
                        <div class="box-body">
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
    </section>
</div>

<%@ include file="../jspf/footer.jspf" %>


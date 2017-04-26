<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="../jspf/management-header.jspf" %>


<div class="content-wrapper">
    <section class="content-header">
        <h1>
            Студенти
        </h1>
        <div class="content">
            <div class="box box-primary">
                <div class="box-header with-border">
                    <form method="get" action="/management/students">
                        <div class="input-group">
                           ${group.name}
                        </div>
                    </form>
                </div>
                <div class="box-body table-responsive">
                    <c:choose>
                        <c:when test="${not empty students}">
                            <table class="table table-hover no-margin">
                                <c:if test="${not empty group.educationProgram}">
                                    <thead>
                                        <tr>
                                            <th></th>
                                            <th></th>
                                            <th class="text-center">Магалего #1</th>
                                            <th class="text-center">Магалего #2</th>
                                            <th class="text-center">Магалего #3</th>
                                            <th class="text-center">Магалего #4</th>
                                        </tr>
                                    </thead>
                                </c:if>
                                <c:forEach items="${students}" var="student">
                                    <tr onclick="document.location = '/management/students/${student.id}';">
                                        <td>
                                            <div class="image">
                                                <img src="/students/${student.id}/photo" class="img-thumbnail student-photo" alt="User Image">
                                            </div>
                                        </td>
                                        <td class="text-center">${student.name} ${student.surname}</td>

                                        <c:set var="isSemesterMagMaynersEmpty" value="${true}"/>
                                        <c:set var="magMaynor1" value=""/>
                                        <c:set var="magMaynor2" value=""/>
                                        <c:set var="magMaynor3" value=""/>
                                        <c:set var="magMaynor4" value=""/>

                                        <c:forEach items="${student.disciplineMarks}" var="mark">
                                            <c:set var="discipline" value="${mark.discipline}"/>
                                            <c:if test="${discipline.type eq 'MAGMAYNOR' and discipline.course eq 1}">
                                                <c:if test="${discipline.semester eq 1}">
                                                    <c:choose>
                                                        <c:when test="${isSemesterMagMaynersEmpty}">
                                                            <c:set var="magMaynor1" value="${discipline.label}"/>
                                                            <c:set var="isSemesterMagMaynersEmpty" value="${false}"/>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <c:set var="magMaynor2" value="${discipline.label}"/>
                                                            <c:set var="isSemesterMagMaynersEmpty" value="${true}"/>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:if>
                                                <c:if test="${discipline.semester eq 2}">
                                                    <c:choose>
                                                        <c:when test="${isSemesterMagMaynersEmpty}">
                                                            <c:set var="magMaynor3" value="${discipline.label}"/>
                                                            <c:set var="isSemesterMagMaynersEmpty" value="${false}"/>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <c:set var="magMaynor4" value="${discipline.label}"/>
                                                            <c:set var="isSemesterMagMaynersEmpty" value="${true}"/>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:if>
                                            </c:if>
                                        </c:forEach>
                                        <td class="text-center">
                                            <c:choose>
                                                <c:when test="${not empty magMaynor1}">${magMaynor1}</c:when>
                                                <c:otherwise><i class="fa fa-remove text-red"></i></c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td class="text-center">
                                            <c:choose>
                                                <c:when test="${not empty magMaynor2}">${magMaynor2}</c:when>
                                                <c:otherwise><i class="fa fa-remove text-red"></i></c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td class="text-center">
                                            <c:choose>
                                                <c:when test="${not empty magMaynor3}">${magMaynor3}</c:when>
                                                <c:otherwise><i class="fa fa-remove text-red"></i></c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td class="text-center">
                                            <c:choose>
                                                <c:when test="${not empty magMaynor4}">${magMaynor4}</c:when>
                                                <c:otherwise><i class="fa fa-remove text-red"></i></c:otherwise>
                                            </c:choose>
                                        </td>
                                    </tr>
                                </c:forEach>
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
    </section>
</div>

<%@ include file="../jspf/management-footer.jspf" %>

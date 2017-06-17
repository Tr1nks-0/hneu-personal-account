<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<%@ include file="../jspf/management-header.jspf" %>

<div class="content-wrapper">
    <div class="content">

        <ol class="breadcrumb panel panel-default">
            <li><i class="fa fa-home"></i></li>
            <li><a href="/management/import/student-marks"><spring:message code="form.label.management.add.totals"/></a></li>
            <li class="active"><spring:message code="form.label.management.imported.student.marks"/></li>
        </ol>

        <tags:error error="${error}"/>
        <tags:success success="${success}"/>

        <div class="panel panel-default">
            <div class="panel-heading">
                <div class="row">
                    <div class="col-md-12 panel-title">
                        <spring:message code="form.label.management.students"/> ${group.name}
                    </div>
                </div>
            </div>

            <div class="panel-body">
            <c:choose>
                <c:when test="${not empty studentsMarks}">
                    <ul class="list-group" id="accordion">
                        <c:forEach items="${studentsMarks}" var="studentMarks" varStatus="i">
                            <c:set var="student" value="${studentMarks.key}"/>
                            <li class="list-group-item">
                                <div>
                                    <a data-toggle="collapse" data-parent="#accordion" href="#collapse${i.index}">${student.surname} ${student.name}</a>
                                    <a href="/management/students/${student.id}" class="float-right">
                                        <i class="fa fa-edit"></i>
                                    </a>
                                </div>
                                <div id="collapse${i.index}" class="panel-collapse collapse">
                                    <table class="table no-margin">
                                        <c:forEach items="${studentMarks.value}" var="mark">
                                            <c:if test="${mark.previousMark ne mark.mark}">
                                                <tr>
                                                    <td>
                                                        ${mark.discipline.label}
                                                        <tags:disciplineType type="${mark.discipline.type}"/>
                                                    </td>
                                                    <td>
                                                        <tags:mark mark="${mark.previousMark}"/>
                                                        <span><i class="fa fa-arrow-right"></i></span>
                                                        <tags:mark mark="${mark.mark}"/>
                                                    </td>
                                                </tr>
                                            </c:if>
                                        </c:forEach>
                                    </table>
                                </div>
                            </li>
                        </c:forEach>
                    </ul>
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

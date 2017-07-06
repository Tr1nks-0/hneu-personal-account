<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<%@ include file="../jspf/management-header.jspf" %>

<div class="content-wrapper">
    <div class="content">

        <ol class="breadcrumb panel panel-default">
            <li><i class="fa fa-home"></i></li>
            <li><a href="/management/import/students-choice"><spring:message code="form.label.management.import.student.choice"/></a></li>
            <li class="active"><spring:message code="form.label.management.imported.student.choice"/></li>
        </ol>

        <tags:error error="${error}"/>
        <tags:success success="${success}"/>

        <div class="panel panel-default">
            <div class="panel-heading">
                <div class="row">
                    <div class="col-md-12 panel-title">
                        <spring:message code="form.label.management.students"/>
                    </div>
                </div>
            </div>

            <div class="panel-body">
            <c:choose>
                <c:when test="${not empty studentsChoice}">
                    <ul class="list-group" id="accordion">
                        <c:forEach items="${studentsChoice}" var="studentChoice" varStatus="i">
                            <c:set var="student" value="${studentChoice.key}"/>
                            <li class="list-group-item">
                                <div>
                                    <a data-toggle="collapse" data-parent="#accordion" href="#collapse${i.index}">${student.surname} ${student.name}</a>
                                    <a href="/management/students/${student.id}" class="float-right">
                                        <i class="fa fa-edit"></i>
                                    </a>
                                </div>
                                <div id="collapse${i.index}" class="panel-collapse collapse">
                                    <table class="table no-margin">
                                        <c:forEach items="${studentChoice.value}" var="choice">
                                            <tr>
                                                <td>${choice.label}</td>
                                                <td><tags:disciplineType type="${choice.type}"/></td>
                                                <td><spring:message code="form.label.student.profile.course"/> ${choice.course}</td>
                                                <td><spring:message code="form.label.student.profile.semester"/> ${choice.semester}</td>
                                            </tr>
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

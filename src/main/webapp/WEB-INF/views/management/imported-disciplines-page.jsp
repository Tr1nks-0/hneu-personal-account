<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<%@ include file="../jspf/management-header.jspf" %>

<div class="content-wrapper">
    <div class="content">

        <ol class="breadcrumb panel panel-default">
            <li><i class="fa fa-home"></i></li>
            <li><a href="/management/import/disciplines"><spring:message code="form.label.management.import.disciplines"/></a></li>
            <li class="active"><spring:message code="form.label.management.imported.disciplines"/></li>
        </ol>

        <tags:error error="${error}"/>
        <tags:success success="${success}"/>

        <div class="panel panel-default">
            <div class="panel-heading">
                <div class="row">
                    <div class="col-md-12 panel-title">
                        <spring:message code="form.label.management.imported.disciplines"/> ${group.name}
                    </div>
                </div>
            </div>

            <div class="panel-body">
            <c:choose>
                <c:when test="${not empty disciplines}">
                    <table class="table no-margin">
                        <tr>
                            <th class="center"><spring:message code="form.label.name"/></th>
                            <th class="center"><spring:message code="form.label.discipline.course"/></th>
                            <th class="center"><spring:message code="form.label.discipline.semester"/></th>
                            <th class="center"><spring:message code="form.label.discipline.degree"/></th>
                        </tr>
                        <c:forEach items="${disciplines}" var="discipline" varStatus="i">
                            <tr>
                                <td>${discipline.label}</td>
                                <td class="center">${discipline.course}</td>
                                <td class="center">${discipline.semester}</td>
                                <td class="center">${discipline.degree.name}</td>
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

<%@ include file="../jspf/management-footer.jspf" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="../jspf/management-header.jspf" %>

<div class="content-wrapper">
    <div class="content">

        <ol class="breadcrumb panel panel-default">
            <li><i class="fa fa-home"></i></li>
            <li><a href="/management/students?groupId=${group.id}">${group.name}</a></li>
            <li class="active"><spring:message code="form.label.management.students"/></li>
        </ol>

        <div class="panel panel-default">
            <div class="panel-heading">
                <div class="row">
                    <div class="col-md-12 panel-title">
                        <spring:message code="form.label.management.students"/> ${group.name}
                    </div>
                </div>
            </div>

            <c:choose>
                <c:when test="${not empty students}">
                    <ul class="list-group">
                        <c:forEach items="${students}" var="student">
                            <li class="list-group-item">
                                <div class="row">
                                    <div class="col-md-12">
                                        <a href="/management/students/${student.id}">${student.surname} ${student.name}</a>
                                    </div>
                                </div>
                            </li>
                        </c:forEach>
                    </ul>
                </c:when>
                <c:otherwise>
                    <div class="panel-body">
                        <spring:message code="items.not.found"/>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>

<%@ include file="../jspf/management-footer.jspf" %>

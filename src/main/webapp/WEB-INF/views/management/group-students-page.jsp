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
                                <c:forEach items="${students}" var="student">
                                    <tr onclick="document.location = '/management/students/${student.id}';">
                                        <td>
                                            <div class="image">
                                                <img src="/students/${student.id}/photo" class="img-thumbnail student-photo" alt="User Image">
                                            </div>
                                        </td>
                                        <td class="text-center">${student.name} ${student.surname}</td>
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

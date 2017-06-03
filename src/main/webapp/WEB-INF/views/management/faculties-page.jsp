<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="info" tagdir="/WEB-INF/tags" %>

<%@ include file="../jspf/management-header.jspf" %>

<div class="content-wrapper">
    <div class="content">

        <ol class="breadcrumb panel panel-default">
            <li><i class="fa fa-home"></i></li>
            <li class="active"><spring:message code="form.label.management.faculties"/></li>
        </ol>

        <form:form modelAttribute="faculty" action="/management/faculties" method="post">

            <info:error error="${error}"/>
            <info:success success="${success}"/>
            <form:errors path="*" cssClass="alert alert-danger alert-dismissible" element="div" />

            <div class="panel panel-default">
                <div class="panel-heading">
                    <div class="row">
                        <div class="col-md-12 panel-title">
                            <spring:message code="form.label.management.faculties.add"/>
                        </div>
                    </div>
                </div>
                <div class="panel-body">
                    <div class="form-group">
                        <label for="name" class="control-label"><spring:message code="form.label.student.faculty"/></label>
                        <form:input path="name" cssClass="form-control" required="required"/>
                    </div>
                    <input type = "submit" value = "<spring:message code="btn.save"/>" class="btn btn-success float-right"/>
                </div>
            </div>
        </form:form>

        <div class="panel panel-default">
            <div class="panel-heading">
                <div class="row">
                    <div class="col-md-12 panel-title">
                        <spring:message code="form.label.management.faculties"/>
                    </div>
                </div>
            </div>
            <div class="panel-body faculties-management-panel">
                <table class="table no-margin">
                    <thead>
                        <tr>
                            <th><spring:message code="form.label.name"/></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${faculties}" var="faculty">
                            <tr>
                                <td>${faculty.name}</td>
                                <td>
                                    <button  data-faculty="${faculty.id}" class="delete-faculty btn btn-danger btn-xs pull-right">
                                        <i class="fa fa-remove"></i>
                                    </button>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<%@ include file="../jspf/management-footer.jspf" %>

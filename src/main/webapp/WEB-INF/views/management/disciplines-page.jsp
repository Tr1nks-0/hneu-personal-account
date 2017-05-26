<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="../jspf/management-header.jspf" %>

<div class="content-wrapper">
    <div class="content">

        <ol class="breadcrumb panel panel-default">
            <li><i class="fa fa-home"></i></li>
            <li><a href="/management/faculties"><spring:message code="form.label.management.faculties"/></a></li>
            <li><a href="/management/specialities"><spring:message code="form.label.management.specialities"/></a></li>
            <li><a href="/management/education-programs"><spring:message code="form.label.management.education.programs"/></a></li>
            <li class="active"><spring:message code="form.label.management.disciplines"/></li>
        </ol>

        <form:form modelAttribute="discipline" action="/management/disciplines" method="post">

            <c:if test="${not empty error}">
                <div class="alert alert-error alert-dismissible">${error}</div>
            </c:if>

            <c:if test="${not empty success}">
                <div class="alert alert-success alert-dismissible"><spring:message code="${success}"/></div>
            </c:if>

            <form:errors path="*" cssClass="alert alert-danger alert-dismissible" element="div" />

            <div class="panel panel-default">
                <div class="panel-heading">
                    <div class="row">
                        <div class="col-md-12 panel-title">
                            <spring:message code="form.label.management.disciplines.add"/>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-6 form-group">
                            <label for="faculty" class="control-label"><spring:message code="form.label.student.faculty"/></label>
                            <select id="faculty"  name="faculty" class="form-control">
                                <c:forEach var="faculty" items="${faculties}">
                                    <option value="${faculty.id}" <c:if test="${faculty.id eq selectedFaculty.id}">selected="selected"</c:if></option> ${faculty.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-md-6 form-group">
                            <label for="speciality" class="control-label"><spring:message code="form.label.student.speciality"/></label>
                            <form:select path="speciality" items="${specialities}" itemLabel="name" itemValue="id" cssClass="form-control"/>
                        </div>
                        <div class="col-md-12 form-group">
                            <label for="educationProgram" class="control-label"><spring:message code="form.label.student.educationProgram"/></label>
                            <form:select path="educationProgram"  cssClass="form-control">
                                <form:option value="">&nbsp;</form:option>
                                <form:options items="${educationPrograms}" itemLabel="name" itemValue="id"/>
                            </form:select>
                        </div>
                    </div>
                </div>
                <div class="panel-body">
                    <div class="col-md-12 form-group">
                       <label for="label" class="control-label"><spring:message code="form.label.name"/></label>
                       <form:input path="label" cssClass="form-control" required="required"/>
                    </div>
                    <div class="col-md-4 form-group">
                       <label for="course" class="control-label"><spring:message code="form.label.student.profile.course"/></label>
                       <form:select path="course" cssClass="form-control" type="number">
                           <c:forEach begin="1" end="6" varStatus="course">
                               <form:option value="${course.index}">${course.index}</form:option>
                           </c:forEach>
                       </form:select>
                    </div>
                    <div class="col-md-4 form-group">
                       <label for="semester" class="control-label"><spring:message code="form.label.student.profile.semester"/></label>
                       <form:select path="semester" cssClass="form-control" type="number">
                           <form:option value="1">1</form:option>
                           <form:option value="2">2</form:option>
                       </form:select>
                    </div>
                    <div class="col-md-4 form-group">
                       <label for="credits" class="control-label"><spring:message code="form.label.discipline.credits"/></label>
                       <form:input path="credits" cssClass="form-control" required="required" type="number"/>
                    </div>
                    <div class="col-md-6 form-group">
                       <label for="type" class="control-label"><spring:message code="form.label.discipline.type"/></label>
                       <form:select path="type" items="${disciplineTypes}" itemLabel="name" cssClass="form-control"/>
                    </div>
                    <div class="col-md-6 form-group">
                       <label for="controlForm" class="control-label"><spring:message code="form.label.discipline.control.form"/></label>
                       <form:select path="controlForm" items="${controlForms}" itemLabel="name" cssClass="form-control"/>
                    </div>
                    <div class="col-md-12">
                        <input type = "submit" value = "<spring:message code="btn.save"/>" class="btn btn-success float-right"/>
                    </div>
                </div>
            </div>
        </form:form>

        <div class="panel panel-default">
            <div class="panel-heading">
                <div class="row">
                    <div class="col-md-12 panel-title">
                         <spring:message code="form.label.management.disciplines"/>
                    </div>
                </div>
            </div>
            <div class="panel-body disciplines-management-panel">
                <c:choose>
                    <c:when test="${not empty disciplines}">
                        <table class="table no-margin">
                            <thead>
                                <tr>
                                    <th><spring:message code="form.label.name"/></th>
                                    <th><spring:message code="form.label.discipline.credits"/></th>
                                    <th><spring:message code="form.label.discipline.control.form"/></th>
                                    <th><spring:message code="form.label.discipline.type"/></th>
                                    <th></th>
                                </tr>
                            <thead>
                            <tbody>
                                <c:forEach items="${disciplines}" var="discipline">
                                    <tr>
                                        <td>${discipline.label}</td>
                                        <td>${discipline.credits}</td>
                                        <td>${discipline.controlForm.name}</td>
                                        <td>${discipline.type.name}</td>
                                        <td>
                                            <button data-discipline="${discipline.id}" class="delete-discipline btn btn-danger btn-xs pull-right">
                                                <i class="fa fa-remove"></i>
                                            </button>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
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
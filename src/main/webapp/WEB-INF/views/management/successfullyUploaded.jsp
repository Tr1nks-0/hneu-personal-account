<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ include file="../jspf/management-header.jspf" %>

<div class="content-wrapper">
    <div class="content">
        <div class="box box-primary">
            <form:form modelAttribute="student" action="/management/students/${profile.id}" method="post">
                <div class="box-header with-border">
                    ${student.email}
                </div>
                <div class="box-body">
                    <div class="row">
                        <div class="col-md-8">
                            <div>
                                <div class="center col-md-4"><spring:message code="form.label.student.profile.name"/></div>
                                <div class="center col-md-8"><form:input path="name" cssClass="editor-input"/></div>
                            </div>
                            <div>
                                <div class="center col-md-4"><spring:message code="form.label.student.profile.surname"/></div>
                                <div class="center col-md-8"><form:input path="surname" cssClass="editor-input"/></div>
                            </div>
                            <div>
                                <div class="center col-md-4"><spring:message code="form.label.student.profile.faculty"/></div>
                                <div class="center col-md-8"><form:input path="passportNumber" cssClass="editor-input"/></div>
                            </div>
                            <div>
                                <div class="center col-md-4"><spring:message code="form.label.student.profile.faculty"/></div>
                                <div class="center col-md-8"><form:input path="faculty" cssClass="editor-input"/></div>
                            </div>
                            <div>
                                <div class="center col-md-4"><spring:message code="form.label.student.profile.speciality"/></div>
                                <div class="center col-md-8"><form:input path="speciality" cssClass="editor-input"/></div>
                            </div>
                            <div>
                                <div class="center col-md-4"><spring:message code="form.label.student.profile.speciality"/></div>
                                <div class="center col-md-8"><form:input path="educationProgram" cssClass="editor-input"/></div>
                            </div>
                            <div>
                                <div class="center col-md-4"><spring:message code="form.label.student.profile.contacts"/></div>
                                <div class="center col-md-8"><form:input path="incomeYear" cssClass="editor-input"/></div>
                            </div>
                            <div>
                                <div class="center col-md-4"><spring:message code="form.label.student.profile.contacts"/></div>
                                <div class="center col-md-8"><form:input path="contactInfo" cssClass="editor-input"/></div>
                            </div>
                            <div>
                                <div class="center col-md-4"><spring:message code="form.label.student.profile.contacts"/></div>
                                <div class="center col-md-8"><form:input path="studentGroup.name" cssClass="editor-input"/></div>
                            </div>
                        </div>
                        <div class="col-md-4">

                        </div>
                    </div>
                    <div class="col-md-12">
                        <c:forEach items="${student.courses}" var="course" varStatus="i">
                            <div class="col-md-12">
                                <span class="label label-info">${course.label}</span>
                            </div>
                            <c:forEach items="${course.semesters}" var="semester" varStatus="j">
                                <div class="col-md-12">
                                    <div class="label label-success">${semester.label}</div>
                                </div>
                                <div class="col-md-12">
                                    <table class="table no-margin">
                                        <c:forEach items="${semester.disciplines}" var="discipline" varStatus="k">
                                            <tr>
                                                <td><form:input path="courses[${i.index}].semesters[${j.index}].disciplines[${k.index}].label" cssClass="editor-input"/></td>
                                                <td><form:input path="courses[${i.index}].semesters[${j.index}].disciplines[${k.index}].credits" cssClass="editor-input"/></td>
                                                <td><form:input path="courses[${i.index}].semesters[${j.index}].disciplines[${k.index}].controlForm" cssClass="editor-input"/></td>
                                                <td>${discipline.type}</td>
                                                <td><form:input path="courses[${i.index}].semesters[${j.index}].disciplines[${k.index}].mark" cssClass="editor-input"/></td>
                                            </tr>
                                            <form:hidden path="courses[${i.index}].semesters[${j.index}].disciplines[${k.index}].id"/>
                                        </c:forEach>
                                    </table>
                                </div>
                                <form:hidden path="courses[${i.index}].semesters[${j.index}].id"/>
                                <form:hidden path="courses[${i.index}].semesters[${j.index}].total"/>
                            </c:forEach>
                            <form:hidden path="courses[${i.index}].id"/>
                        </c:forEach>
                    </div>

                    <form:hidden path="average"/>
                    <form:hidden path="rate"/>
                    <form:hidden path="photo"/>
                </div>
            </form:form>
        </div>
    </div>
</div>

<%@ include file="../jspf/management-footer.jspf" %>

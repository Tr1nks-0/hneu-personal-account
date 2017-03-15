<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ include file="../jspf/management-header.jspf" %>

<div class="content-wrapper">
    <section class="content-header">
        <h1>
            <spring:message code="form.label.student.profile.head"/>
            <small><spring:message code="form.label.student.profile.head.second"/></small>
        </h1>
        <form:form modelAttribute="student" action="/management/students/${profile.id}" method="post">
            <div class="content">
                <div class="box box-info">
                    <div class="box-body">
                        <div class="col-md-12">
                            <div class="float-right">
                                <a href="/management/students"  class="btn btn-warning"><spring:message code="btn.cancel"/></a>
                                <input type="submit" class="btn btn-success" value="<spring:message code="btn.save"/>"/>
                                <a href="/management/removeStudent?id=${profile.id}" class="btn btn-danger" ><spring:message code="btn.remove"/></a>
                            </div>
                        </div>
                        <div class="image col-md-3">
                            <c:set var="profileImage" value="/profile/${profile.profileImage}"/>
                            <c:if test="${empty profile.profileImage}">
                                <c:set var="profileImage" value="/resources/img/student_photo_default.png"/>
                            </c:if>
                            <img src="${profileImage}" class="center img-circle photo-editor" alt="User Image">
                        </div>
                        <div class="table-responsive col-md-9">
                            <table class="table no-margin no-border">
                                <tr>
                                    <td class="center col-md-4"><spring:message code="form.label.student.profile.surname"/></td>
                                    <td class="center col-md-8"><form:input path="surname" cssClass="editor-input"/></td>
                                </tr>
                                <tr>
                                    <td class="center col-md-4"><spring:message code="form.label.student.profile.name"/></td>
                                    <td class="center col-md-8"><form:input path="name" cssClass="editor-input"/></td>
                                </tr>
                                <tr>
                                    <td class="center col-md-4"><spring:message code="form.label.student.profile.faculty"/></td>
                                    <td class="center col-md-8"><form:input path="faculty" cssClass="editor-input"/></td>
                                </tr>
                                <tr>
                                    <td class="center col-md-4"><spring:message code="form.label.student.profile.speciality"/></td>
                                    <td class="center col-md-8"><form:input path="speciality" cssClass="editor-input"/></td>
                                </tr>
                                <tr>
                                    <td class="center col-md-4"><spring:message code="form.label.student.profile.group"/></td>
                                    <td class="center col-md-8"><form:input path="group" cssClass="editor-input"/></td>
                                </tr>
                                <tr>
                                    <td class="center col-md-4"><spring:message code="form.label.student.profile.contacts"/></td>
                                    <td class="center col-md-8"><form:input path="contactInfo" cssClass="editor-input"/></td>
                                </tr>
                            </table>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
                                <c:forEach items="${profile.courses}" var="course" varStatus="i">
                                    <div class="col-md-12"><span class="label label-info">${course.label}</span></div>
                                    <c:forEach items="${course.semesters}" var="semester" varStatus="j">
                                        <div class="col-md-12"><div class="label label-success">${semester.label}</div></div>
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
                                                    <form:hidden path="courses[${i.index}].semesters[${j.index}].disciplines[${k.index}].rowInExcelFile"/>
                                                </c:forEach>
                                            </table>
                                        </div>
                                        <form:hidden path="courses[${i.index}].semesters[${j.index}].id"/>
                                        <form:hidden path="courses[${i.index}].semesters[${j.index}].total"/>
                                    </c:forEach>
                                    <form:hidden path="courses[${i.index}].id"/>
                                </c:forEach>
                                <form:hidden path="incomeYear"/>
                                <form:hidden path="groupId"/>
                                <form:hidden path="password"/>
                                <form:hidden path="profileImage"/>
                                <form:hidden path="passportNumber"/>
                                <form:hidden path="filePath"/>
                                <form:hidden path="modificationTime"/>
                                <form:hidden path="average"/>
                                <form:hidden path="specialityPlace"/>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </form:form>
    </section>
</div>

<%@ include file="../jspf/management-footer.jspf" %>

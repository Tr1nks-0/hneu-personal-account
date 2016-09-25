<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ include file="../jspf/management-header.jspf" %>

<div class="content-wrapper">
    <section class="content-header">
        <h1>
            <spring:message code="form.label.student.profile.head"/>
            <small><spring:message code="form.label.student.profile.head.second"/></small>
        </h1>
        <form:form modelAttribute="profile" action="/management/students/${profile.id}" method="post">
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
                    </div>
                </div>
            </div>
        </form:form>
    </section>
</div>

<%@ include file="../jspf/management-footer.jspf" %>

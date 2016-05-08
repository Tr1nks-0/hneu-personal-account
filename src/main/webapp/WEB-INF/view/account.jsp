<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="jspf/header.jspf" %>

<div class="container" id="main-container">
    <div class="row">
        <form:form action="account/update" method="POST" commandName="profile">
            <div class="row">
                <div class="col-md-7 col-sm-8 col-xs-12">
                    <div class="row">
                        <div class="col-md-4 col-sm-4 col-xs-5 profile-label"><strong><spring:message
                                code="form.label.student.profile.surname"/></strong></div>
                        <div class="col-md-8 col-sm-8 col-xs-7 profile-value"><form:input path="surname"
                                                                                          type="text"/></div>
                    </div>
                    <div class="row">
                        <div class="col-md-4 col-sm-4 col-xs-5 profile-label"><strong><spring:message
                                code="form.label.student.profile.name"/></strong></div>
                        <div class="col-md-8 col-sm-8 col-xs-7 profile-value"><form:input path="name"
                                                                                          type="text"/></div>
                    </div>
                    <div class="row">
                        <div class="col-md-4 col-sm-4 col-xs-5 profile-label"><strong><spring:message
                                code="form.label.student.profile.faculty"/></strong></div>
                        <div class="col-md-8 col-sm-8 col-xs-7 profile-value"><form:input path="faculty"
                                                                                          type="text"/></div>
                    </div>
                    <div class="row">
                        <div class="col-md-4 col-sm-4 col-xs-5 profile-label"><strong><spring:message
                                code="form.label.student.profile.incomeYear"/></strong></div>
                        <div class="col-md-8 col-sm-8 col-xs-7 profile-value"><form:input path="incomeYear"
                                                                                          type="text"/></div>
                    </div>
                    <div class="row">
                        <div class="col-md-4 col-sm-4 col-xs-5 profile-label"><strong><spring:message
                                code="form.label.student.profile.contacts"/></strong></div>
                        <div class="col-md-8 col-sm-8 col-xs-7 profile-value">
                            <c:forEach items="${profile.contactInfo}" varStatus="i">
                                <div class="row">
                                    <form:input path="contactInfo[${i.index}]" type="text"/>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-4 col-sm-4 col-xs-5 profile-label"><strong><spring:message
                                code="form.label.student.profile.speciality"/></strong></div>
                        <div class="col-md-8 col-sm-8 col-xs-7 profile-value"><form:input path="speciality"
                                                                                          type="text"/></div>
                    </div>
                </div>
                <div class="col-md-5 col-sm-4 hidden-xs"></div>
            </div>

            <div class="row">
                <div class="col-md-6 col-sm-12 col-xs-12">
                    <table class="table table-bordered">
                        <thead>
                        <tr>
                            <th class="col-md-1 col-sm-1 col-xs-1"><spring:message
                                    code="form.label.student.profile.table.tab1"/></th>
                            <th class="col-md-6 col-sm-6 col-xs-6"><spring:message
                                    code="form.label.student.profile.table.tab2"/></th>
                            <th class="col-md-1 col-sm-1 col-xs-1"><spring:message
                                    code="form.label.student.profile.table.tab3"/></th>
                            <th class="col-md-3 col-sm-3 csol-xs-3"><spring:message
                                    code="form.label.student.profile.table.tab4"/></th>
                            <th class="col-md-1 col-sm-1 col-xs-1"><spring:message
                                    code="form.label.student.profile.table.tab5"/></th>
                        </tr>
                        </thead>
                    </table>
                </div>
                <div class="col-md-6 hidden-xs hidden-sm">
                    <table class="table table-bordered">
                        <thead>
                        <tr>
                            <th class="col-md-1 col-sm-1 col-xs-1"><spring:message
                                    code="form.label.student.profile.table.tab1"/></th>
                            <th class="col-md-6 col-sm-6 col-xs-6"><spring:message
                                    code="form.label.student.profile.table.tab2"/></th>
                            <th class="col-md-1 col-sm-1 col-xs-1"><spring:message
                                    code="form.label.student.profile.table.tab3"/></th>
                            <th class="col-md-3 col-sm-3 col-xs-3"><spring:message
                                    code="form.label.student.profile.table.tab4"/></th>
                            <th class="col-md-1 col-sm-1 col-xs-1"><spring:message
                                    code="form.label.student.profile.table.tab5"/></th>
                        </tr>
                        </thead>
                    </table>
                </div>
            </div>

            <div class="row total-rows">
                <c:forEach items="${profile.courses}" varStatus="i">
                    <div class="row text-center">
                        <strong>${i.index + 1} <spring:message code="form.label.student.profile.course"/></strong>
                    </div>
                    <div class="row">
                        <c:forEach items="${profile.courses.get(i.index).semesters}" varStatus="j">
                            <div class="col-md-6 col-sm-12 col-xs-12">
                                <div class="row">
                                    <strong><form:input path="courses[${i.index}].semesters[${j.index}].label"
                                                        type="text" cssClass="text-center"/></strong>
                                </div>
                                <div class="row">
                                    <table class="table table-bordered">
                                        <c:forEach
                                                items="${profile.courses.get(i.index).semesters.get(j.index).disciplines}"
                                                varStatus="q">
                                            <tr>
                                                <td class="col-md-1 col-sm-1 col-xs-1"></td>
                                                <td class="col-md-6 col-sm-6 col-xs-6"><form:input
                                                        path="courses[${i.index}].semesters[${j.index}].disciplines[${q.index}].label"
                                                        type="text"/></td>
                                                <td class="col-md-1 col-sm-1 col-xs-1"><form:input
                                                        path="courses[${i.index}].semesters[${j.index}].disciplines[${q.index}].credits"
                                                        type="text"/></td>
                                                <td class="col-md-3 col-sm-3 col-xs-3"><form:input
                                                        path="courses[${i.index}].semesters[${j.index}].disciplines[${q.index}].controlForm"
                                                        type="text"/></td>
                                                <td class="col-md-1 col-sm-1 col-xs-1"><form:input
                                                        path="courses[${i.index}].semesters[${j.index}].disciplines[${q.index}].mark"
                                                        type="text"/></td>
                                            </tr>
                                        </c:forEach>
                                    </table>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </c:forEach>
            </div>
        </form:form>
    </div>
</div>
<%@ include file="jspf/footer.jspf" %>

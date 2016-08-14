<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ include file="../jspf/header.jspf" %>

<div class="content-wrapper">
    <section class="content-header">
        <h1>
            <spring:message code="form.label.student.profile.head"/>
            <small><spring:message code="form.label.student.profile.head.second"/></small>
        </h1>
        <div class="content">
            <div class="col-md-6 col-sm-12 col-xs-12">
                <div class="info-box">
                    <span class="info-box-icon bg-green"><i class="ion ion-university"></i></span>

                    <div class="info-box-content">
                        <span class="info-box-text">
                            <spring:message code="form.label.student.profile.faculty"/> <c:out value="${profile.faculty}"/>
                        </span>
                        <span class="info-box-text">
                            <spring:message code="form.label.student.profile.incomeYear"/> <c:out value="${profile.incomeYear}"/>
                        </span>
                        <span class="info-box-text">
                            <spring:message code="form.label.student.profile.group"/> <c:out value="${profile.group}"/>
                        </span>
                    </div>
                </div>
            </div>
            <div class="col-md-6 col-sm-12 col-xs-12">
                <div class="info-box">
                    <span class="info-box-icon bg-yellow"><i class="ion ion-ios-telephone-outline"></i></span>
                    <div class="info-box-content">
                        <span class="info-box-text">
                            <spring:message code="form.label.student.profile.contacts"/>
                        </span>
                        <div>
                            <c:forEach items="${profile.contactInfo}" varStatus="i" var="contact">
                                <span class="info-box-number">
                                    <small><c:out value="${contact}"/></small>
                                </span>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>
            <div>
                <c:forEach items="${profile.courses}" varStatus="i" var="course">
                    <div>
                        <h3 class="box-title">
                            <span class="course-label badge bg-light-blue" id="${i.index + 1}course">
                                ${i.index + 1} <spring:message code="form.label.student.profile.course"/>
                            </span>
                        </h3>
                        <c:forEach items="${course.semesters}" varStatus="j" var="semester">
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="box box-info">
                                        <div class="box-header with-border">
                                            <h4 class="box-title">
                                                <span class="label label-success"><c:out value="${semester.label}"/></span>
                                            </h4>
                                            <div class="box-tools pull-right">
                                                <button type="button" class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-minus"></i></button>
                                            </div>
                                        </div>
                                        <div class="box-body">
                                            <div class="table-responsive">
                                                <table class="table no-margin">
                                                    <thead>
                                                        <tr>
                                                            <th><spring:message code="form.label.student.profile.table.tab1"/></th>
                                                            <th class="center hidden-xs"><spring:message code="form.label.student.profile.table.tab2"/></th>
                                                            <th class="center hidden-xs"><spring:message code="form.label.student.profile.table.tab3"/></th>
                                                            <th class="center"><spring:message code="form.label.student.profile.table.tab4"/></th>
                                                        </tr>
                                                    </thead>

                                                    <tbody>
                                                        <c:forEach items="${semester.disciplines}" var="discipline">
                                                            <tr>
                                                                <td><c:out value="${discipline.label}"/></td>
                                                                <td class="center hidden-xs"><c:out value="${discipline.credits}"/></td>
                                                                <td class="center hidden-xs"><c:out value="${discipline.controlForm}"/></td>
                                                                <td class="center"><c:out value="${discipline.mark}"/></td>
                                                            </tr>
                                                        </c:forEach>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </c:forEach>
            </div>
        </div>
    </section>
</div>

<%@ include file="../jspf/footer.jspf" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="html" uri="http://struts.apache.org/tags-html" %>
<%@ page contentType="text/html;charset=UTF-8" %>

<%@ include file="../jspf/header.jspf" %>

<style>
    .average-mark {
        text-align: center;
        font-size: 30px;
        font-weight: lighter;
    }
</style>

<div class="content-wrapper">
    <section class="content-header">
        <h1>
            <spring:message code="form.label.student.profile.head"/>
            <small><spring:message code="form.label.student.profile.head.second"/></small>
        </h1>
        <div class="content">
            <div class="row student-info">
                <div>
                    <div class="col-md-6 col-sm-12 col-xs-12 border-bottom border-right">
                        <span class="info-box-icon bg-white"><i class="ion-ios-book-outline"></i></span>
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
                    <div class="col-md-6 col-sm-12 col-xs-12 border-bottom">
                        <span class="info-box-icon bg-white"><i class="ion ion-ios-telephone-outline"></i></span>
                        <div class="info-box-content">
                            <span class="info-box-text">
                                <spring:message code="form.label.student.profile.contacts"/>
                            </span>
                            <div>
                                <c:forEach items="${profile.contactInfo}" varStatus="i" var="contact">
                                    <span class="info-box-number info-box-cropped">
                                        <small><c:out value="${contact}"/></small>
                                    </span>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-4 hidden-xs hidden-sm info-box-no-content border-right">
                        <form method="post" id="studentForm" name="studentForm" action="http://services.ksue.edu.ua:8081/student/report" target="marks-window">
                            <input type="hidden" size="15" name="lastName" value="${profile.surname}">
                            <input type="hidden" size="15" name="code" value="${profile.passportNumber}">
                        </form>
                        <a class="info-box-icon bg-white current-marks border-bottom">
                            <div class="img"><i class="ion ion-ios-analytics-outline"></i></div>
                            <div class="text"><span>Поточні оцінки</span></div>
                        </a>
                    </div>
                    <div class="col-sm-12 col-xs-12 hidden-md hidden-lg border-right current-marks border-bottom">
                        <span class="info-box-icon bg-white"><i class="ion ion-ios-analytics-outline"></i></span>
                        <div class="info-box-content">
                            <a class="info-box-text info-box-text-center bg-white">
                                <span>Поточні оцінки</span>
                            </a>
                        </div>
                    </div>
                    <div class="col-md-4 hidden-xs hidden-sm info-box-no-content border-right">
                        <a class="info-box-icon bg-white" href="${profile.filePath}" download>
                            <div class="img"><i class="ion-ios-download-outline"></i></div>
                            <div class="text"><span>Завантажити план</span></div>
                        </a>
                    </div>
                    <div class="col-sm-12 col-xs-12 hidden-md hidden-lg border-right">
                        <a class="info-box-icon bg-white" href="${profile.filePath}" download>
                            <span class="info-box-icon bg-white"><i class="ion-ios-download-outline"></i></span>
                        </a>
                        <div class="info-box-content">
                            <a class="info-box-text info-box-text-center bg-white" href="${profile.filePath}" download>
                                Завантажити план
                            </a>
                        </div>
                    </div>
                    <div class="col-md-4 hidden-xs hidden-sm info-box-no-content border-right">
                        <div class="info-box-icon bg-white text average-mark">
                            <div>${profile.specialityPlace}. ${profile.average}</div>
                        </div>
                    </div>
                    <div class="col-sm-12 col-xs-12 hidden-md hidden-lg border-right">
                        <a class="info-box-icon bg-white" href="${profile.filePath}" download>
                            <span class="info-box-icon bg-white"><i class="ion-ios-download-outline"></i></span>
                        </a>
                        <div class="info-box-content">
                            <a class="info-box-text info-box-text-center bg-white" href="${profile.filePath}" download>
                                Завантажити план
                            </a>
                        </div>
                    </div>
                </div>
            </div>

            <div>
                <c:forEach items="${profile.courses}" varStatus="i" var="course">
                    <c:set var="isNotCurrentCourse" value="${currentCourse != i.index + 1}"/>

                    <div>
                        <h3 class="box-title">
                            <span class="course-label badge bg-light-blue" id="${i.index + 1}course">
                                ${i.index + 1} <spring:message code="form.label.student.profile.course"/>
                            </span>
                        </h3>
                        <c:forEach items="${course.semesters}" varStatus="j" var="semester">
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="box box-info <c:if test="${isNotCurrentCourse}">collapsed-box</c:if>">
                                        <div class="box-header with-border">
                                            <h4 class="box-title">
                                                <span class="label label-success"><c:out value="${semester.label}"/></span>
                                            </h4>
                                            <div class="box-tools pull-right">
                                                <button type="button" class="btn btn-box-tool" data-widget="collapse">
                                                    <c:choose>
                                                        <c:when test="${isNotCurrentCourse}"><i class="fa fa-plus"></i></c:when>
                                                        <c:otherwise><i class="fa fa-minus"></i></c:otherwise>
                                                    </c:choose>
                                                </button>
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
                                                            <c:if test="${not empty discipline.label}">
                                                                <tr>
                                                                    <td><c:out value="${discipline.label}"/></td>
                                                                    <td class="center hidden-xs"><c:out value="${discipline.credits}"/></td>
                                                                    <td class="center hidden-xs"><c:out value="${discipline.controlForm}"/></td>
                                                                    <td class="center">
                                                                        <c:choose>
                                                                            <c:when test="${discipline.mark ge '90' or discipline.mark eq 'з' }">
                                                                                <button class="btn btn-success disabled">
                                                                                    <c:out value="${discipline.mark}"/>
                                                                                </button>
                                                                            </c:when>
                                                                            <c:when test="${discipline.mark ge '74' and discipline.mark lt '90' }">
                                                                                <button class="btn btn-default disabled">
                                                                                    <c:out value="${discipline.mark}"/>
                                                                                </button>
                                                                            </c:when>
                                                                            <c:when test="${discipline.mark ge '60' and discipline.mark lt '74' }">
                                                                                <button class="btn btn-warning disabled">
                                                                                    <c:out value="${discipline.mark}"/>
                                                                                </button>
                                                                            </c:when>
                                                                            <c:when test="${(discipline.mark ge '0' and discipline.mark lt '60') or discipline.mark eq 'н'}">
                                                                                <button class="btn btn-danger disabled">
                                                                                    <c:out value="${discipline.mark}"/>
                                                                                </button>
                                                                            </c:when>
                                                                        </c:choose>
                                                                    </td>
                                                                </tr>
                                                            </c:if>
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


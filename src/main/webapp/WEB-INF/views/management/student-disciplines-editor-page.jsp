<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="../jspf/management-header.jspf" %>

<style>
    .selectpicker-left-from-control {
        border-top-left-radius: 5px;
        border-bottom-left-radius: 5px;
        -webkit-appearance: none;
    }
</style>

<div class="content-wrapper">
    <div class="content">
        <form:form modelAttribute="disciplineMark" action="/management/students/${student.id}/disciplines" method="post" data-student="${student.id}">

            <ol class="breadcrumb panel panel-default">
                <li><a href="/management/students?groupId=${student.group.id}">${student.group.name}</a></li>
                <li><a href="/management/students/${student.id}">${student.name} ${student.surname}</a></li>
                <li class="active"><spring:message code="form.label.management.disciplines"/></li>
            </ol>

            <div class="panel panel-default">
                <div class="panel-heading">
                    <div class="row">
                        <div class="col-md-2 col-xs-4 orm-group">
                            <label for="course" class="control-label"><spring:message code="form.label.student.profile.course"/></label>
                            <select id="course" class="form-control" type="number">
                                <c:forEach items="${courses}" var="course">
                                    <option value="${course}" <c:if test="${selectedCourse eq course}">selected</c:if>>${course}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-md-2 col-xs-4 form-group">
                            <label for="semester" class="control-label"><spring:message code="form.label.student.profile.semester"/></label>
                            <select id="semester" class="form-control" type="number">
                                <option value="1" <c:if test="${selectedSemester eq 1}">selected</c:if>>1</option>
                                <option value="2" <c:if test="${selectedSemester eq 2}">selected</c:if>>2</option>
                            </select>
                        </div>
                    </div>
                </div>

                <div class="panel-body">
                    <c:if test="${not empty error}">
                        <div class="col-md-12">
                            <div class="alert alert-error alert-dismissible"><spring:message code="${error}"/></div>
                        </div>
                    </c:if>

                    <c:if test="${not empty success}">
                        <div class="col-md-12">
                            <div class="alert alert-success alert-dismissible"><spring:message code="${success}"/></div>
                        </div>
                    </c:if>

                    <form:errors path="*" cssClass="alert alert-danger alert-dismissible" element="div" />

                    <label for="discipline" class="control-label"><spring:message code="form.label.management.disciplines"/></label>
                    <div class="input-group">
                        <form:select path="discipline" cssClass="selectpicker selectpicker-left-from-control form-control">
                            <c:forEach items="${disciplines}" var="discipline">
                                <form:option value="${discipline.id}" label="${discipline.type.name} - ${discipline.label}"/>
                            </c:forEach>
                        </form:select>
                        <div class="input-group-btn">
                            <input type = "submit" value = "<spring:message code="btn.save"/>"
                                   <c:if test="${empty disciplines}">disabled="true"</c:if> class="btn btn-success float-right"/>
                        </div>
                    </div>
                    <form:hidden path="student"/>
                </div>
            </div>
        </form:form>

        <div class="panel panel-default">
            <div class="panel-heading">
                <spring:message code="form.label.management.disciplines"/>
            </div>
            <div id="student-disciplines" class="panel-body">
                <c:choose>
                    <c:when test="${not empty marks}">
                        <table class="table table-hover no-margin">
                            <thead>
                                <tr>
                                    <th><spring:message code="form.label.name"/></th>
                                    <th class="center hidden-xs"><spring:message code="form.label.discipline.credits"/></th>
                                    <th class="center hidden-xs"><spring:message code="form.label.discipline.control.form"/></th>
                                    <th class="center hidden-xs"><spring:message code="form.label.discipline.type"/></th>
                                    <th></th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${marks}" var="mark">
                                    <tr>
                                        <td>${mark.discipline.label}</td>
                                        <td class="center hidden-xs">${mark.discipline.credits}</td>
                                        <td class="center hidden-xs">${mark.discipline.controlForm.name}</td>
                                        <td class="center hidden-xs">${mark.discipline.type.name}</td>
                                        <td>
                                            <button class="btn btn-danger btn-xs delete-student-discipline"
                                                    <c:if test="${mark.discipline.type eq 'REGULAR'}">disabled</c:if>
                                                    data-discipline="${mark.id}">
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

<script>

    $("#disciplineMark #course").change(redirectWithPickedCourseAndSemester);

    $("#disciplineMark #semester").change(redirectWithPickedCourseAndSemester);

    $(".delete-student-discipline").click(function() {
        var student = getStudentId();
        var disciplineMark = $(this).data("discipline");
        var container = $(this).closest("tr")
        $.post( "/management/students/" + student + "/disciplines/" + disciplineMark + "/delete", function() {
            container.fadeOut(300, function(){
                $(this).remove();
                if(!$(".delete-student-discipline").length) {
                    $("#student-disciplines").html("<spring:message code='items.not.found'/>");
                }
            });
        });
    });

    function redirectWithPickedCourseAndSemester() {
        var student = getStudentId();
        var course = $("#disciplineMark #course option:selected").val();
        var semester = $("#disciplineMark #semester option:selected").val();
        window.location.href="/management/students/" + student + "/disciplines?course=" + course + "&semester=" + semester;
    }

    function getStudentId() {
        return $("#disciplineMark").data("student");
    }

</script>

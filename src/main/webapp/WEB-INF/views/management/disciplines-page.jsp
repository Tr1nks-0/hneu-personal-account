<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="../jspf/management-header.jspf" %>

<div class="content-wrapper">
    <section class="content-header">
        <h1>
            <spring:message code="form.label.management.disciplines"/>
        </h1>
        <div class="content">
            <div class="box box-primary">
                <div class="box-header with-border">
                    <spring:message code="form.label.management.disciplines.add"/>
                </div>
                <div class="box-body">

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

                    <form:form modelAttribute="discipline" action="/management/disciplines" method="post">
                        <form:errors path="*" cssClass="alert alert-danger alert-dismissible" element="div" />
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
                        <input type = "submit" value = "<spring:message code="btn.save"/>" class="btn btn-success float-right"/>
                    </form:form>
                </div>
            </div>

            <div class="box box-primary">
                <div class="box-header with-border">
                    <spring:message code="form.label.management.disciplines"/>
                </div>
                <div class="disciplines-box box-body">
                    <c:choose>
                        <c:when test="${not empty disciplines}">
                            <table class="table no-margin">
                                <tr>
                                    <th><spring:message code="form.label.name"/></th>
                                    <th><spring:message code="form.label.discipline.credits"/></th>
                                    <th><spring:message code="form.label.discipline.control.form"/></th>
                                    <th><spring:message code="form.label.discipline.type"/></th>
                                    <th></th>
                                </tr>
                                <c:forEach items="${disciplines}" var="discipline">
                                    <tr>
                                        <td>${discipline.label}</td>
                                        <td>${discipline.credits}</td>
                                        <td>${discipline.controlForm.name}</td>
                                        <td>${discipline.type.name}</td>
                                        <td><button  discipline-id="${discipline.id}" class="remove-discipline btn btn-danger float-right">X</button></td>
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
    </section>
</div>

<%@ include file="../jspf/management-footer.jspf" %>

<script>

    $("#faculty").change(function() {
        var faculty = $("#faculty option:selected").val();
        window.location.href="/management/disciplines?facultyId=" + faculty;
    });

    $("#speciality").change(function() {
        var faculty = $("#faculty option:selected").val();
        var speciality = $("#speciality option:selected").val();
        window.location.href="/management/disciplines?facultyId=" + faculty + "&specialityId=" + speciality;
    });

    $("#educationProgram").change(function() {
        var faculty = $("#faculty option:selected").val();
        var speciality = $("#speciality option:selected").val();
        var educationProgram = $("#educationProgram option:selected").val();
        window.location.href="/management/disciplines?facultyId=" + faculty + "&specialityId=" + speciality
            + "&educationProgramId=" +educationProgram;
    });


    $("#course").change(function() {
        var faculty = $("#faculty option:selected").val();
        var speciality = $("#speciality option:selected").val();
        var educationProgram = $("#educationProgram option:selected").val();
        var course = $("#course option:selected").val();
        var semester = $("#semester option:selected").val();
        window.location.href="/management/disciplines?facultyId=" + faculty + "&specialityId=" + speciality
            + "&educationProgramId=" +educationProgram
            + "&course=" + course
            + "&semester=" + semester;
    });

    $("#semester").change(function() {
        var faculty = $("#faculty option:selected").val();
        var speciality = $("#speciality option:selected").val();
        var educationProgram = $("#educationProgram option:selected").val();
        var course = $("#course option:selected").val();
        var semester = $("#semester option:selected").val();
        window.location.href="/management/disciplines?facultyId=" + faculty + "&specialityId=" + speciality
            + "&educationProgramId=" +educationProgram
            + "&course=" + course
            + "&semester=" + semester;
    });

    $(".remove-discipline").click(function() {
        var group = $(this).attr("discipline-id");
        var container = $(this).closest("tr")
        $.post( "/management/disciplines/" + group + "/delete", function() {
            container.fadeOut(300, function(){
                $(this).remove();
                if(!$(".remove-discipline").length) {
                    $(".disciplines-box").html("<spring:message code='items.not.found'/>");
                }
            });
        });
    });

</script>

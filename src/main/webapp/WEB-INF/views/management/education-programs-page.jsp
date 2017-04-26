<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="../jspf/management-header.jspf" %>

<div class="content-wrapper">
    <section class="content-header">
        <h1>
            <spring:message code="form.label.management.education.programs"/>
        </h1>
        <div class="content">
            <div class="box box-primary">
                <div class="box-header with-border">
                    <spring:message code="form.label.management.education.programs.add"/>
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

                    <form:form modelAttribute="educationProgram" action="/management/education-programs" method="post">
                        <form:errors path="*" cssClass="alert alert-danger alert-dismissible" element="div" />
                        <div class="form-group">
                            <label for="faculty" class="control-label"><spring:message code="form.label.student.faculty"/></label>
                            <select id="faculty"  name="faculty" class="form-control">
                                <c:forEach var="faculty" items="${faculties}">
                                    <option value="${faculty.id}" <c:if test="${faculty.id eq selectedFaculty.id}">selected="selected"</c:if></option> ${faculty.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="speciality" class="control-label"><spring:message code="form.label.student.speciality"/></label>
                            <form:select path="speciality" items="${specialities}" itemLabel="name" itemValue="id" cssClass="form-control"/>
                        </div>
                        <div class="form-group">
                            <label for="name" class="control-label"><spring:message code="form.label.student.educationProgram"/></label>
                            <form:input path="name" cssClass="form-control" required="required"/>
                        </div>
                        <input type = "submit" value = "<spring:message code="btn.save"/>" class="btn btn-success float-right"/>
                    </form:form>
                </div>
            </div>

            <div class="box box-primary">
                <div class="box-header with-border">
                    <spring:message code="form.label.management.education.programs"/>
                </div>
                <div class="specialities-box box-body">
                    <c:choose>
                        <c:when test="${not empty educationPrograms}">
                            <table class="table no-margin">
                                <tr>
                                    <th><spring:message code="form.label.name"/></th>
                                    <th><spring:message code="form.label.student.speciality"/></th>
                                    <th></th>
                                </tr>
                                <c:forEach items="${educationPrograms}" var="educationProgram">
                                    <tr>
                                        <td>${educationProgram.name}</td>
                                        <td>${educationProgram.speciality.name}</td>
                                        <td><button  education-program-id="${educationProgram.id}" class="remove-education-program btn btn-danger float-right">X</button></td>
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
        window.location.href="/management/education-programs?facultyId=" + faculty;
    });

    $("#speciality").change(function() {
        var faculty = $("#faculty option:selected").val();
        var speciality = $("#speciality option:selected").val();
        window.location.href="/management/education-programs?facultyId=" + faculty + "&specialityId=" + speciality;
    });

    $(".remove-education-program").click(function() {
        var speciality = $(this).attr("education-program-id");
        var container = $(this).closest("tr")
        $.post( "/management/education-programs/" + speciality + "/delete", function() {
            container.fadeOut(300, function(){
                $(this).remove();
                if(!$(".remove-education-program").length) {
                    $(".education-programs-box").html("<spring:message code='items.not.found'/>");
                }
            });
        });
    });

</script>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="../jspf/management-header.jspf" %>

<div class="content-wrapper">
    <section class="content-header">
        <h1>
            <spring:message code="form.label.management.specialities"/>
        </h1>
        <div class="content">
            <div class="box box-primary">
                <div class="box-header with-border">
                    <spring:message code="form.label.management.specialities.add"/>
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

                    <form:form modelAttribute="speciality" action="/management/specialities" method="post">
                        <form:errors path="*" cssClass="alert alert-danger alert-dismissible" element="div" />
                        <div class="form-group">
                            <label for="faculty" class="control-label"><spring:message code="form.label.student.faculty"/></label>
                            <form:select path="faculty" items="${faculties}" itemLabel="name" itemValue="id" cssClass="form-control"/>
                        </div>
                        <div class="form-group">
                            <label for="name" class="control-label"><spring:message code="form.label.student.speciality"/></label>
                            <form:input path="name" cssClass="form-control" required="required"/>
                        </div>
                        <input type = "submit" value = "<spring:message code="btn.save"/>" class="btn btn-success float-right"/>
                    </form:form>
                </div>
            </div>

            <div class="box box-primary">
                <div class="box-header with-border">
                    <spring:message code="form.label.management.specialities"/>
                </div>
                <div class="specialities-box box-body">
                    <c:choose>
                        <c:when test="${not empty specialities}">
                            <table class="table no-margin">
                                <tr>
                                    <th><spring:message code="form.label.name"/></th>
                                    <th><spring:message code="form.label.student.faculty"/></th>
                                    <th></th>
                                </tr>
                                <c:forEach items="${specialities}" var="speciality">
                                    <tr>
                                        <td>${speciality.name}</td>
                                        <td>${speciality.faculty.name}</td>
                                        <td><button speciality-id="${speciality.id}" class="remove-speciality btn btn-danger float-right">X</button></td>
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
        window.location.href="/management/specialities?facultyId=" + faculty;
    });

    $(".remove-speciality").click(function() {
        var speciality = $(this).attr("speciality-id");
        var container = $(this).closest("tr")
        $.post( "/management/specialities/" + speciality + "/delete", function() {
            container.fadeOut(300, function(){
                $(this).remove();
                if(!$(".remove-speciality").length) {
                    $(".specialities-box").html("<spring:message code='items.not.found'/>");
                }
            });
        });
    });

</script>

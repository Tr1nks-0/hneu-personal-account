<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="../jspf/management-header.jspf" %>

<div class="content-wrapper">
    <section class="content-header">
        <h1>
            <spring:message code="form.label.management.faculties"/>
        </h1>
        <div class="content">
            <div class="box box-primary">
                <div class="box-header with-border">
                    <spring:message code="form.label.management.faculties.add"/>
                </div>
                <div class="box-body">

                    <c:if test="${not empty error}">
                        <div class="col-md-12">
                            <div class="alert alert-error alert-dismissible">
                                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">x</button>
                                <i class="icon fa fa-error"></i> ${error}
                            </div>
                        </div>
                    </c:if>

                    <form:form modelAttribute="newFaculty" action="/management/faculties/create" method="post">
                        <div class="form-group">
                            <label for="name" class="control-label"><spring:message code="form.label.student.faculty"/></label>
                            <form:input path="name" cssClass="form-control" required="required"/>
                        </div>
                        <input type = "submit" value = "<spring:message code="btn.save"/>" class="btn btn-success float-right"/>
                    </form:form>
                </div>
            </div>

            <div class="box box-primary">
                <div class="box-header with-border">
                    <spring:message code="form.label.management.faculties"/>
                </div>
                <div class="faculties-box box-body">
                    <table class="table table-hover no-margin">
                        <tr>
                            <th><spring:message code="form.label.name"/></th>
                        </tr>
                        <c:forEach items="${faculties}" var="faculty">
                            <tr>
                                <td>${faculty.name}</td>
                                <td><button  faculty-id="${faculty.id}" class="remove-faculty btn btn-danger float-right">X</button></td>
                            </tr>
                        </c:forEach>
                    </table>
                </div>
            </div>
        </div>
    </section>
</div>

<%@ include file="../jspf/management-footer.jspf" %>

<script>

$(".remove-faculty").click(function() {
    var faculty = $(this).attr("faculty-id");
    var container = $(this).closest("tr")
    $.post( "/management/faculties/" + faculty + "/delete", function() {
        container.fadeOut(300, function(){
            $(this).remove();
            if(!$(".remove-faculty").length) {
                $(".faculties-box").html("<spring:message code='items.not.found'/>");
            }
        });
    });
});

</script>

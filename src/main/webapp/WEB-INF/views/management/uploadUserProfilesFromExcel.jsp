<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="../jspf/management-header.jspf" %>

<div class="content-wrapper">
    <section class="content-header">
        <h1>
            <spring:message code="form.label.management.add.profiles"/>
        </h1>
        <div class="content">
            <div class="box box-primary">
                <div class="box-header with-border">
                    <spring:message code="form.label.management.add.profiles.message"/>
                </div>
                <div class="box-body">
                    <form method="post" action="/management/uploadStudentProfilesFromExcel" modelAttribute="uploadForm"
                          enctype="multipart/form-data">
                        <div class="input-group">
                            <span class="input-group-btn">
                                <span class="btn btn-primary btn-file">
                                    <spring:message code="form.label.management.browse"/>&hellip;
                                    <input name="files" type="file" multiple
                                           placeholder="<spring:message code="form.label.management.profiles"/>"/>
                                </span>
                            </span>
                            <input type="text" class="form-control" readonly>
                            <span class="input-group-btn">
                                <input type="submit" value="<spring:message code="form.label.management.download"/>"
                                       class="btn btn-default"/>
                            </span>
                        </div>
                        <input type="hidden"  name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </form>
                </div>
            </div>
        </div>
    </section>
</div>

<%@ include file="../jspf/management-footer.jspf" %>

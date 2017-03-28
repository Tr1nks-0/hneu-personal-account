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

                    <c:if test="${not empty error}">
                        <div class="col-md-12">
                            <div class="alert alert-error alert-dismissible">
                                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">x</button>
                                <i class="icon fa fa-error"></i> ${error}
                            </div>
                        </div>
                    </c:if>

                    <form method="post" action="/management/import/student" enctype="multipart/form-data">
                        <div class="input-group">
                            <span class="input-group-btn">
                                <span class="btn btn-primary btn-file">
                                    <spring:message code="form.label.management.browse"/>&hellip;
                                    <input name="file" type="file" placeholder="<spring:message code="form.label.management.profiles"/>"/>
                                </span>
                            </span>
                            <input type="text" class="form-control" readonly>
                            <span class="input-group-btn">
                                <input type="submit" value="<spring:message code="form.label.management.download"/>" class="btn btn-default"/>
                            </span>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
</div>

<%@ include file="../jspf/management-footer.jspf" %>

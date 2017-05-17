<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="../jspf/management-header.jspf" %>

<div class="content-wrapper">
    <div class="content">

        <ol class="breadcrumb panel panel-default">
            <li><i class="fa fa-home"></i></li>
            <li class="active"><spring:message code="form.label.management.add.profiles"/></li>
        </ol>

        <div class="panel panel-default">
            <div class="panel-heading">
                <div class="row">
                    <div class="col-md-12">
                        <spring:message code="form.label.management.add.profiles.message"/>
                    </div>
                </div>
            </div>
            <div class="panel-body">
                <c:if test="${not empty error}">
                    <div class="col-md-12">
                        <div class="alert alert-error alert-dismissible">${error}</div>
                    </div>
                </c:if>

                <form method="post" action="/management/import/student" enctype="multipart/form-data">
                    <div class="input-group">
                        <span class="input-group-btn">
                            <span class="btn btn-default btn-file">
                                <spring:message code="form.label.management.browse"/>&hellip;
                                 <input name="file" type="file" placeholder="<spring:message code="form.label.management.profiles"/>"/>
                            </span>
                        </span>
                        <input type="text" class="form-control" readonly>
                        <span class="input-group-btn">
                            <input type="submit" value="<spring:message code="form.label.management.download"/>" class="btn btn-success"/>
                        </span>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<%@ include file="../jspf/management-footer.jspf" %>

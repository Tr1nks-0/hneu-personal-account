<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="info" tagdir="/WEB-INF/tags" %>

<%@ include file="../jspf/management-header.jspf" %>

<style>
    #import-doc-example > img {
        max-width: 100%;
    }
</style>

<div class="content-wrapper">
    <div class="content">

        <ol class="breadcrumb panel panel-default">
            <li><i class="fa fa-home"></i></li>
            <li class="active"><spring:message code="form.label.management.import.profiles"/></li>
        </ol>

        <info:error error="${error}"/>
        <info:success success="${success}"/>

        <div class="panel panel-default">
            <div class="panel-heading">
                <div class="row">
                    <div class="col-md-12">
                        <spring:message code="form.label.management.add.profiles.message"/>
                        <a class="link" data-toggle="collapse" href="#import-doc-example" aria-expanded="false" aria-controls="import-doc-example">
                            <spring:message code="form.label.management.import.doc.example"/>
                        </a>
                    </div>
                </div>
            </div>
            <div class="panel-body">
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
            <div class="panel-footer text-center collapse" id="import-doc-example">
                <img src="/resources/img/profile-download-img.png"/>
            </div>
        </div>
    </div>
</div>

<%@ include file="../jspf/management-footer.jspf" %>

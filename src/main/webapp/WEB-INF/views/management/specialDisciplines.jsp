<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="../jspf/management-header.jspf" %>
<div class="content-wrapper">
    <section class="content-header">
        <h1>
            Група ${group} курс ${course} семестр ${semester}
        </h1>
        <div class="content">
            <div class="box box-primary">
                <div class="box-header with-border">
                    <h4 class="box-title">
                        <span class="label label-success">МАЙНОР</span>
                    </h4>
                    <div class="box-tools pull-right">
                        <button type="button" class="btn btn-box-tool" data-widget="collapse">
                            <i class="fa fa-plus"></i>
                        </button>
                    </div>
                </div>
                <form:form method="post" action="/management/maynors/${group}/${course}/${semester}" modelAttribute="maynors">
                    <div class="box-body table-responsive">
                        <c:if test="${not empty maynors}">
                            <table class="table table-hover no-margin">
                                <c:forEach items="${maynors.list}" var="discipline" varStatus="i">
                                    <tr>
                                        <td width="20%">${discipline.surname}</td>
                                        <td width="20%">${discipline.name}</td>
                                        <td width="50%" class="text-center">
                                            <form:input path="list[${i.index}].label"/>
                                        </td>
                                        <td width="10%" class="text-center">
                                            <form:input path="list[${i.index}].mark"/>
                                        </td>
                                    </tr>
                                    <form:hidden path="list[${i.index}].studentId"/>
                                </c:forEach>
                            </table>
                            <button type="submit" class="btn btn-danger col-md-12 text-center">
                                Save
                            </button>
                        </c:if>
                    </div>
                </form:form>
            </div>
        </div>
    </section>
</div>

<%@ include file="../jspf/management-footer.jspf" %>

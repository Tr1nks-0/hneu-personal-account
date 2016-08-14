<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ include file="../jspf/management-header.jspf" %>

<div class="content">
    <div class="box box-primary">
        <div class="box-header with-border">
            Завантажені файли
        </div>
        <div class="box-body">
            <ul class="list-group">
                <c:forEach items="${files.entrySet()}" var="file">
                    <c:choose>
                        <c:when test="${file.value}"><li class="list-group-item list-group-item-success"></c:when>
                        <c:otherwise><li class="list-group-item list-group-item-danger"></c:otherwise>
                    </c:choose>
                    ${file.key}</li>
                </c:forEach>
            </ul>
        </div>
    </div>
</div>

<%@ include file="../jspf/management-footer.jspf" %>

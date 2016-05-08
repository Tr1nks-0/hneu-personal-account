<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="jspf/header.jspf" %>
<%@ include file="jspf/management-header.jspf" %>
<ul class="list-group">
    <c:forEach items="${files.entrySet()}" var="file">
        <c:choose>
            <c:when test="${file.value}"><li class="list-group-item list-group-item-success"></c:when>
            <c:otherwise><li class="list-group-item list-group-item-danger"></c:otherwise>
        </c:choose>
       ${file.key}</li>
    </c:forEach>
</ul>
<%@ include file="jspf/management-footer.jspf" %>
<%@ include file="jspf/footer.jspf" %>

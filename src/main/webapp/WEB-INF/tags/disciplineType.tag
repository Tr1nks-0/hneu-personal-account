<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="type" required="false" type="edu.hneu.studentsportal.enums.DisciplineType"%>

<c:choose>
    <c:when test="type eq 'MAJOR'">
        <span class="label label-default">
    </c:when>
    <c:when test="type eq 'MAYNOR'">
        <span class="label label-info">
    </c:when>
    <c:when test="type eq 'MAGMAYNOR'">
        <span class="label label-success">
    </c:when>
    <c:otherwise>
        <span class="label label-primary">
    </c:otherwise>
</c:choose>
    ${type.name}
</span>

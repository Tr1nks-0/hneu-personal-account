<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="type" required="true" type="edu.hneu.studentsportal.enums.DisciplineType"%>

<c:choose>
    <c:when test="type eq 'MAJOR'">
        <span class="label label-default">
                ${type.name}
        </span>
    </c:when>
    <c:when test="type eq 'MAYNOR'">
        <span class="label label-info">
                ${type.name}
        </span>
    </c:when>
    <c:when test="type eq 'MAGMAYNOR'">
        <span class="label label-success">
                ${type.name}
        </span>
    </c:when>
    <c:otherwise>
        <span class="label label-primary">
                ${type.name}
        </span>
    </c:otherwise>
</c:choose>

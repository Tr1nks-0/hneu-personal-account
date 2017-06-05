<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="mark" required="true" type="java.lang.String" %>

<c:choose>
    <c:when test="${mark ge '90' or mark eq 'з' }">
        <label class="label label-success">
            <c:out value="${mark}"/>
        </label>
    </c:when>
    <c:when test="${mark ge '74' and mark lt '90' }">
        <label class="label label-default">
            <c:out value="${mark}"/>
        </label>
    </c:when>
    <c:when test="${mark ge '60' and mark lt '74' }">
        <label class="label label-warning">
            <c:out value="${mark}"/>
        </label>
    </c:when>
    <c:when test="${(mark ge '0' and mark lt '60') or mark eq 'н'}">
        <label class="label label-danger">
            <c:out value="${mark}"/>
        </label>
    </c:when>
</c:choose>

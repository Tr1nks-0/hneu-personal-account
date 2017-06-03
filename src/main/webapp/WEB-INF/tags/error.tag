<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="error" required="false" type="java.lang.String"%>

<c:if test="${not empty error}">
    <div class="alert alert-error alert-dismissible">${error}</div>
</c:if>
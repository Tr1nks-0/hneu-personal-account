<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="jspf/header.jspf"%>
    <%@ include file="jspf/management-header.jspf"%>
        <ul class="list-group">
            <c:forEach items="${files}" var="file">
                <li class="list-group-item">${file}</li>
            </c:forEach>
        </ul>
    <%@ include file="jspf/management-footer.jspf"%>
<%@ include file="jspf/footer.jspf"%>

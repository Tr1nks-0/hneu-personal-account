<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="jspf/header.jspf"%>
<div class="container">
    <h2>Basic List Group</h2>
    <ul class="list-group">
        <c:forEach items="${files}" var="file">
            <li class="list-group-item">${file}</li>
        </c:forEach>
    </ul>
<div>
<%@ include file="jspf/footer.jspf"%>

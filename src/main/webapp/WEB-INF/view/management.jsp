<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="jspf/header.jspf"%>

<form:form method="post" action="/management/upload"
		   modelAttribute="uploadForm" enctype="multipart/form-data">
	<input name="files" type="file" multiple />
	<input type="submit" value="Upload" />
	<input id="addFile" type="button" value="Add File" />
</form:form>

<%@ include file="jspf/footer.jspf"%>

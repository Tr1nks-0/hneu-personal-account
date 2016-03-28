<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="jspf/header.jspf"%>

<form:form method="post" action="/account/save"
		   modelAttribute="uploadForm" enctype="multipart/form-data">

	<p>Select files to upload. Press Add button to add more file inputs.</p>

	<input id="addFile" type="button" value="Add File" />
	<table id="fileTable">
		<tr>
			<td><input name="files[0]" type="file" /></td>
		</tr>
		<tr>
			<td><input name="files[1]" type="file" /></td>
		</tr>
	</table>
	<br/><input type="submit" value="Upload" />
</form:form>
<script>
	$(document).ready(function() {
		//add more file components if Add is clicked
		$('#addFile').click(function() {
			var fileIndex = $('#fileTable tr').children().length - 1;
			$('#fileTable').append(
					'<tr><td>'+
					'   <input type="file" name="files['+ fileIndex +']" />'+
					'</td></tr>');
		});

	});
</script>
<%@ include file="jspf/footer.jspf"%>

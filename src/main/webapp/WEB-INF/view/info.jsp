<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="jspf/header.jspf" %>

<form:form method="post" action="/account/save"
           modelAttribute="uploadForm" enctype="multipart/form-data">

    <p>Select files to upload. Press Add button to add more file inputs.</p>

    <input id="addFile" type="button" value="Add File"/>
    <table id="fileTable">
        <tr>
            <td><input name="files[0]" type="file"/></td>
        </tr>
        <tr>
            <td><input name="files[1]" type="file"/></td>
        </tr>
    </table>
    <br/><input type="submit" value="Upload"/>
</form:form>
<script>
    $(document).ready(function () {
        //add more file components if Add is clicked
        $('#addFile').click(function () {
            var fileIndex = $('#fileTable tr').children().length - 1;
            $('#fileTable').append(
                    '<tr><td>' +
                    '   <input type="file" name="files[' + fileIndex + ']" />' +
                    '</td></tr>');
        });

    });
</script>


<%--<!-- Header Carousel -->
<header id="myCarousel" class="carousel slide">
	<!-- Indicators -->
	<ol class="carousel-indicators">
		<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
		<li data-target="#myCarousel" data-slide-to="1"></li>
		<li data-target="#myCarousel" data-slide-to="2"></li>
	</ol>

	<!-- Wrapper for slides -->
	<div class="carousel-inner">
		<div class="item active">
			<div class="fill" style="background-image:url('http://placehold.it/1900x1080&text=Slide One');"></div>
			<div class="carousel-caption">
				<h2>Caption 1</h2>
			</div>
		</div>
		<div class="item">
			<div class="fill" style="background-image:url('http://placehold.it/1900x1080&text=Slide Two');"></div>
			<div class="carousel-caption">
				<h2>Caption 2</h2>
			</div>
		</div>
		<div class="item">
			<div class="fill" style="background-image:url('http://placehold.it/1900x1080&text=Slide Three');"></div>
			<div class="carousel-caption">
				<h2>Caption 3</h2>
			</div>
		</div>
	</div>

	<!-- Controls -->
	<a class="left carousel-control" href="#myCarousel" data-slide="prev">
		<span class="icon-prev"></span>
	</a>
	<a class="right carousel-control" href="#myCarousel" data-slide="next">
		<span class="icon-next"></span>
	</a>
</header>--%>

<%@ include file="jspf/footer.jspf" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<!DOCTYPE html>
<html>
<head>

	<title>Load Project</title>

	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">


	<link rel="icon" href="resources/icons/pi.png">

	<!-- Variable for schema evolution -->
	<c:set var="schemaEvo" value="${schemas}" />
	
	<!-- Variable for available projects -->
	<c:set var="prj" value="${projects}" />

	<!-- Variable for counting the available options -->
	<c:set var="cnt" value="${0}" />

	<!-- Latest compiled and minified CSS -->
	<link rel="stylesheet"
		href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

	<!-- jQuery library -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js">
	</script>

	<!-- Latest compiled JavaScript -->
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js">
	</script>

	<link rel="stylesheet" href="resources/upload-style.css">

	<script src="resources/include.js" type="text/javascript"></script>

	<script type="text/javascript"> 
		
 		$(document).ready(function(){ 
 			includeHTML();
			
		});
 		
	</script>
	
	<script src="resources/activeNav.js" type="text/javascript"></script>

</head>
<body>

	
	<div id="nav" w3-include-html="nav.html"></div>


	<div id="opt_container">

		<form action="loader" method="post" accept-charset="UTF-8" enctype="multipart/form-data">
			<label>Projects available: ${fn:length(prj)}</label>
			<br/>
			<c:set var="cnt" value="${0}"/>
			<c:forEach items="${prj}" var="pr">
				<label class="radio-inline"><input type="radio" name="optradio" value="${cnt}">${pr}</label>
				<c:set var="cnt" value="${cnt+1}"/>
			</c:forEach>
			<br/>
			<input type="submit" value="Load">
			<input id="sch" value="${schemas}" type="hidden"/> 
		</form>

	</div>

	<div id="footer-container" w3-include-html="footer.html"></div>


</body>
</html>
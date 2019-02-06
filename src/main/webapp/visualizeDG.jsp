<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	
	<meta charset="ISO-8859-1">
	
	 <meta name="viewport" content="width=device-width, initial-scale=1">
	
	<title>Visualize</title>
	
	<link rel="icon" href="resources/icons/pi.png">
	
	<!-- Variable for DG nodes -->
	<c:set var="nodes" value="${DGNodes}" />
	
	<!-- Variable for DG links -->
	<c:set var="links" value="${DGLinks}" />

	<!-- Variable for the selected groupId -->
	<c:set var="selGroupId" value="${gId}" />

	<!-- Variable for the selected radius -->
	<c:set var="selRadiusId" value="${rId}" />

	<!-- Number of versions -->
	<c:set var="versionsNum" value="${vNum}" />

	<!-- Variable for current version -->
	<c:set var="currVersion" value="${cVersion}" />

	<!-- Latest compiled and minified CSS -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

	<!-- jQuery library -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

	<!-- Latest compiled JavaScript -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	
	<link rel="stylesheet" href="resources/visualize-DG-style.css">
	
	<!-- D3 library -->
	<script  src="https://d3js.org/d3.v4.min.js"></script>
	<script src="http://d3js.org/d3-selection-multi.v1.js"></script>
	
	<!-- Plotly library -->
	<script src="https://cdn.plot.ly/plotly-latest.min.js"></script>
  	<!-- Numeric JS -->
  	<script src="https://cdnjs.cloudflare.com/ajax/libs/numeric/1.2.6/numeric.min.js"></script>
	
	<!-- Functions that draw the 4 evo patterns -->
	<script type="text/javascript" src="resources/dg-viz.js" ></script>
	
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
	
	<div id="container">

		<div class="form-group">
			<label for="groupId">Group by</label>
			<form action="" id="group-form"  accept-charset="UTF-8" method="GET">
				
				<select class="form-control" id="groupId" name="groupId">
					<option value="0" <c:out value="${selGroupId == '0'?'selected':''}"></c:out>>
						Birth Version
					 </option>
					<option value="1" <c:out value="${selGroupId == '1'?'selected':''}"></c:out>>
						Update Activity
					</option>
					<option value="2" <c:out value="${selGroupId == '2'?'selected':''}"></c:out>>
						(Non-) Survivors
					</option>
				</select>
				
				<label for="radiusId">Radius</label>
				<select class="form-control" id="radiusId" name="radiusId">
					<option value="0" <c:out value="${selRadiusId == '0'?'selected':''}"></c:out>>
						Size@Birth
					</option>
					<option value="1" <c:out value="${selRadiusId == '1'?'selected':''}"></c:out>>
						Duration
					</option>
				</select>
				
				<label>Version</label>
				<input type="number" name="versionId" id="versionId"
				 min="1" max=<c:out value="${versionsNum}"></c:out> value="${currVersion}">
				<br/>
				<label>Zoom in/out</label>
				<input type="range" min="0" max="1" step="any" value="0.5">

				<input name="param1" value="dg" type="hidden">

			</form>
			

		
		</div><!-- End of form-group -->

		<div class="scatter-DG" id="scatterDG"></div>
	
		<div id="pie"><!-- Contains a pie chart with info about the 
					   	   breakdown of nodes over the groups -->
		</div>
	
	
	</div> <!-- End of container -->
	
	

	<form action="">
			<input id="nod" value='${nodes}' type="hidden">
			<input id="lin" value='${links}' type="hidden">
	</form>
	
	<div id="footer-container" w3-include-html="footer.html"></div>

</body>
</html>
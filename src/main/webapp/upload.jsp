<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

	<title>File Upload</title>
	<link rel="icon" href="resources/icons/pi.png">

	<!-- Variable for schema evolution -->
	<c:set var="schemaEvo" value="${schemas}" />
	
	<!-- Latest compiled and minified CSS -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

	<!-- jQuery library -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

	<!-- Latest compiled JavaScript -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	
	<link rel="stylesheet" href="resources/upload-style.css">
	
	<script  src="https://d3js.org/d3.v4.min.js"></script>
	
	<script type="text/javascript" src="resources/simple-line.js" ></script>

	<script src="resources/include.js" type="text/javascript"></script>
	
	<script>
		function drawCharts(){
		    
		    //Disable draw button
		    document.getElementById("drawBtn").disabled = true;
		    var str = '${schemaEvo}';
		    SIMPLE_LINE.init(str);
		    SIMPLE_LINE.drawLineChart();
		}
		

		
	</script>
	
 	<script type="text/javascript"> 
		
 	
 		$(document).ready(function(){ 
 			
 		  	includeHTML();
			
 		  	var menuItems = $("#statsMenu li a");
 		    
 			$("#statsMenu li a").on('click',function(e){
 			    
 			    e.preventDefault();
 			    menuItems.parent().removeClass('active');
 			    $(this).parent().addClass('active');
 			    
  			   
 			    var parent_id = e.target.parentNode.id;
 			  
 			    changeInputValue(parent_id); 
 			  
 			    document.getElementById("chngStatsForm").submit();
 			    
 			});
 			

			
		});
 		
		function changeInputValue(id){
		    
		    document.getElementById("statsId").value = id;
				
		}
 		
	
	</script>
	
	<script src="resources/activeNav.js" type="text/javascript"></script>
	
</head>

<body >
	
	
	<!-- Nav bar container -->
	<div id="nav" w3-include-html="nav.html"></div>

	
	<form action="uploader" method="post" accept-charset="UTF-8" enctype="multipart/form-data">
		
		<label for="prjName">Project Name:</label>
		<input type="text" name="projectName" size="40" placeholder="Enter the project name" id="prjName">
		<br/>
		<label for="fileSel">Select sql files:</label>
		<input type="file" name="file" size="100" multiple class="form-control-file" id="fileSel">
		<br/>
		<input id = "subBtn" type="submit" value="Upload files" class="btn btn-default">
		<input id="sch" value="${schemas}" type="hidden"/> 
		
	</form>
	
	<form id = "chngStatsForm" action="loader" method="post" accept-charset="UTF-8">
		
		<input id="statsId" name="statsId" value="0" type="hidden"/> 
	
	</form>
	
	<table id="t1" class="table table-striped">
		<thead>
			<tr>
				<th style="text-align:center">Overall Statistics</th>
			</tr>
			<tr>
				<th>Feature</th>
				<th>Value</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${stats}" var="stat">
				<tr>
					<td>${stat.key}</td>
					<td class="tbl-value">${stat.value}</td>
				</tr>
			</c:forEach>
		</tbody>
	
	</table>
	
	<table id="t2" class="table table-striped">
		<thead>
			<tr>
				<th style="text-align:center">Statistics Overview</th>
			</tr>
			<tr>
				<th>Categories</th>
				<th>Number of Tables</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${statsOver}" var="statOver">
				<tr>
					<td>${statOver.key}</td>
					<td class="tbl-value">${statOver.value}</td>
				</tr>
			</c:forEach>
		</tbody>
	
	</table>
	
	<ul id="statsMenu" class="breadcrumb">
  		<li class="active" id="1"><a href="#">Birth</a></li>
  		<li id="2"><a href="#" >Survival</a></li>
  		<li id="3"><a href="#">Duration</a></li>
  		<li id="4"><a href="#">Activity</a></li>
	</ul> 
	
 	<button id="drawBtn" class="btn btn-default" onclick="drawCharts()">Draw</button>
	
	<div class="chart-area"></div>
	
	<div id="footer-container" w3-include-html="footer.html"></div>

</body>
</html>
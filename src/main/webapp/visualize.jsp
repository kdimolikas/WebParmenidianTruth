<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	
	<meta charset="ISO-8859-1">
	
	<title>Visualize</title>
	
	<link rel="icon" href="resources/icons/pi.png">
	
	<!-- Variable for evolution patterns -->
	<c:set var="EvoPatt" value="${patt}" />

	<!-- Variable for Gamma Pattern values -->
	<c:set var="GammaData" value="${gammaValues}"/>
	
	<!-- Variable for Inverse Gamma Pattern values -->
	<c:set var="InvGammaData" value="${invGammaValues}" />
	
	<!-- Variable for Commet Pattern values -->
	<c:set var="CommetData" value="${commetValues}" />
	
	<!-- Variable for Empty Triangle Pattern values -->
	<c:set var="TriangleData" value="${triangleValues}" />

	<!-- Latest compiled and minified CSS -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

	<!-- jQuery library -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

	<!-- Latest compiled JavaScript -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	
	<link rel="stylesheet" href="resources/visualize-style.css">
	
	<script  src="https://d3js.org/d3.v4.min.js"></script>
	
	<!-- Functions that draw the 4 evo patterns -->
	<script type="text/javascript" src="resources/patterns-viz.js" ></script>
	
	
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
	
	<div id="parent1">
		<table id="t1" class="table table-striped">
			<thead>
				<tr>
					<th style="text-align:center" colspan="4">Tables' Size vs Duration(Gamma Pattern)</th>
				</tr>
				<tr>
					<td></td>
					<th colspan="3">Duration (% wrt total #versions)</th>
				</tr>
				<tr>
					<th>Size at Birth (in #attributes)</th>
					<td>&lt;0.33</td>
					<td>&ge;0.33 and &le;0.77</td>
					<td>&gt;0.77</td>
				</tr>
			</thead>
			
			<tbody>
				<c:forEach items="${gammaStats}" var="gamma">
					<tr>
						<td>${gamma.key}</td>
						<c:forEach items="${gamma.value}" var="val">
							<td>${val}</td>
						</c:forEach>
					</tr>
				</c:forEach>
			</tbody>
		
		</table>

		<div class="scatter" id="scatter1"></div>
		
		<form action="">
			<input id="gamma" value='${GammaData}' type="hidden">
		</form>
		
		<button class="btn btn-default" id="btn1" >Draw</button>
	
	</div> <!-- End of parent1 -->

	<div id="parent2">
		<table id="t2" class="table table-striped">
			<thead>
				<tr>
					<th style="text-align:center" colspan="4">Tables' Duration vs sum(Updates)
						(Inverse Gamma Pattern)</th>
				</tr>
				<tr>
					<td></td>
					<th colspan="3">sum(Updates)</th>
				</tr>
				<tr>
					<th>Duration (% wrt total #versions)</th>
					<td>0(Rigid)</td>
					<td>&le;5(Quiet)</td>
					<td>&gt;5(Active)</td>
				</tr>
			</thead>
			
			<tbody>
				<c:forEach items="${invGammaStats}" var="invGamma">
					<tr>
						<td>${invGamma.key}</td>
						<c:forEach items="${invGamma.value}" var="val1">
							<td>${val1}</td>
						</c:forEach>
					</tr>
				</c:forEach>
			</tbody>
		
		</table>
		
		<form action="">
			<input id="invGamma" value='${InvGammaData}' type="hidden">
		</form>
		
		<div class="scatter" id="scatter2"></div>
		
		<button class="btn btn-default" id="btn2">Draw</button>
		
	</div> <!-- End of parent2 -->
	
	
	<div id="parent3">
		<table id="t3" class="table table-striped">
			<thead>
				<tr>
					<th style="text-align:center" colspan="4">Tables' Size vs sum(Updates)
						 (Commet Pattern)</th>
				</tr>
				<tr>
					<td></td>
					<th colspan="3">sum(Updates)</th>
				</tr>
				<tr>
					<th>Size at Birth (in #attributes)</th>
					<td>0(Rigid)</td>
					<td>&le;5(Quiet)</td>
					<td>&gt;5(Active)</td>
				</tr>
			</thead>
			
			<tbody>
				<c:forEach items="${commetStats}" var="commet">
					<tr>
						<td>${commet.key}</td>
						<c:forEach items="${commet.value}" var="val2">
							<td>${val2}</td>
						</c:forEach>
					</tr>
				</c:forEach>
			</tbody>
		
		</table>
		
		<div class="scatter" id="scatter3"></div>
		
		<form action="">
			<input id="commet" value='${CommetData}' type="hidden">
		</form>
		
		<button class="btn btn-default" id="btn3">Draw</button>
		
	</div> <!-- End of parent3 -->
	
	
	<div id="parent4">
		<table id="t4" class="table table-striped">
			<thead>
				<tr>
					<th style="text-align:center" colspan="4">Tables' Birth Version vs 
						Duration (Empty Triangle Pattern)</th>
				</tr>
				<tr>
					<td></td>
					<th colspan="3">Duration (% wrt total #versions)</th>
				</tr>
				<tr>
					<th>Birth Version (% wrt total #versions)</th>
					<td>&lt;0.33</td>
					<td>&ge;0.33 and &le;0.77</td>
					<td>&gt;0.77</td>
				</tr>
			</thead>
			
			<tbody>
				<c:forEach items="${emptyTriangleStats}" var="eTriangle">
					<tr>
						<td>${eTriangle.key}</td>
						<c:forEach items="${eTriangle.value}" var="val3">
							<td>${val3}</td>
						</c:forEach>
					</tr>
				</c:forEach>
			</tbody>
		
		</table>
		
		<div class="scatter" id="scatter4"></div>
		
		<form action="">
			<input id="eTriangle" value='${TriangleData}' type="hidden">
		</form>
				
		<button class="btn btn-default" id="btn4">Draw</button>
		
	</div> <!-- End of parent4 -->
	
	<div id="footer-container" w3-include-html="footer.html"></div>

</body>
</html>
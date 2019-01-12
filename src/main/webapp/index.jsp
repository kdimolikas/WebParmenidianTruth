<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>Home</title>
	
	<link rel="icon" href="resources/icons/pi.png">
	
	<!-- Latest compiled and minified CSS -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">

	<!-- jQuery library -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

	<!-- Latest compiled JavaScript -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	
	<script src="resources/include.js" type="text/javascript"></script>
	
	<script type="text/javascript">
		
		$(document).ready(function(){
		    
		    includeHTML();

		});
		
		
	</script>
	
	<script src="resources/activeNav.js" type="text/javascript"></script>
	
	<style>
	
		#footer-container{
			position:relative;
			bottom:0;
			margin-top:40px;
		}
	
	
	</style>
	
	
</head>

<body style="width:100%" >

<!-- 	<div style="top:0;left:0"> -->
<!-- 		<h1>Parmenidian Truth</h1> -->
<!-- 	</div> -->
	<div id="nav" w3-include-html="nav.html"></div>

	<div id="footer-container" w3-include-html="footer.html"></div>
	
</body>
</html>


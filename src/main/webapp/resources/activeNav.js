window.onload=function() {
   
    var url = window.location.pathname;
    $('#nav').find('li .active').removeClass('active');
   
    if (url=="/upload.jsp" || url=="/loader"){
	
	$("#nav").find("#l2").addClass("active");
	
    }else if (url=="/index.jsp" || url==""){
	
	$("#nav").find("#l1").addClass("active");
	
    }else{
	
	$("#nav").find("#l3").addClass("active");
	
    }
    
};

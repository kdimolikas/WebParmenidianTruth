$(document).ready(function(){
    
    
    	$("#btn1").click(function(){
    	    
    	    var data ={};
    	    data = $("#gamma").val();
    	    drawGammaPattern(data);
    	    $(this).prop('disabled', true);
    	    
    	});
    
    	$("#btn2").click(function(){
    	    
    	    var data ={};
    	    data = $("#invGamma").val();
    	    drawInvGammaPattern(data);
    	    $(this).prop('disabled', true);
    	    
    	});
    	
    	$("#btn3").click(function(){
    	    
    	    var data ={};
    	    data = $("#commet").val();
    	    drawCommetPattern(data);
    	    $(this).prop('disabled', true);
    	    
    	});
    
    	
    	$("#btn4").click(function(){
    	    
    	    var data ={};
    	    data = $("#eTriangle").val();
    	    drawEmptyTrianglePattern(data);
    	    $(this).prop('disabled', true);
    	    
    	});

});


function getActivityClass(d){
	if (d==0)
	    return "RIGID";
	else if (d==1)
	    return "QUIET";
	else if (d==2)
	    return "ACTIVE";	
}


//Draws the Gamma pattern   
function drawGammaPattern(data){
	    
    try{
	//alert(data);
	var arr = JSON.parse(data); 
	console.log(arr);
    }catch (e){
	alert(e);
    }
    
    var margin = {top:30, right:30, bottom:50, left:70},
	    width = 800 - margin.left - margin.right,
	    height = 400 - margin.top - margin.bottom;
	
	
	//add graph canvas to the jsp 
    var svg = d3.select('#scatter1')
	.append('svg')
	.attr('width', width+margin.right+margin.left)
	.attr('height', height + margin.top + margin.bottom)
	.append('g')
	.attr('transform', 'translate(' + margin.left + ',' + margin.top + ')');
	

	
    //setup radius
    var radius = d3.scaleSqrt().range([2,5]);

    //add domain to avoid overlapping
    var xMax = d3.max(arr,(d)=>d.xValue);
    var xScale = d3.scaleLinear().range([0,width-margin.right])
	.domain([1,xMax])
		.nice();
    var xAxis = d3.axisBottom()
    	.scale(xScale)
    	.ticks(4);
	
    var yScale = d3.scaleLinear().range([height,0])
		.domain([1,d3.max(arr, (d)=>d.yValue)])
		.nice();
    var yAxis = d3.axisLeft().scale(yScale);
	

	
    radius.domain(d3.extent(arr,function(d){
		
	return d.rad;
		
    })).nice();
	
	
    //setup fill color
//    var activity = [];
//    arr.forEach(function(d,i){
//
//	    activity[i] = getActivityClass(d.col);
//
//    });
    
    
   // var colors = [];
    var color = d3.scaleOrdinal(d3.schemeCategory10)
		.domain(arr.map(function(d){return getActivityClass(d.col);}));
		
	
	    
    svg.append('g')
		.attr('transform', 'translate(0,' + height + ')')
		.attr('class', 'x axis')
		.call(xAxis);

    // y-axis is translated to (0,0)
    svg.append('g')
		.attr('transform', 'translate(0,0)')
		.attr('class', 'y axis')
		.call(yAxis);
	
	    
    //draw bubbles
    var bubble = svg.selectAll('.bubble')
		.data(arr)
		.enter().append('circle')
		.attr('class', 'bubble')
		.attr('cx', function(d){return xScale(d.xValue);})
		.attr('cy', function(d){ return yScale(d.yValue);})
		.attr('r', function(d){ return radius(d.rad);})
		.style('fill', function(d,i){ return color(getActivityClass(d.col));});
	
	    
    bubble.append('title')
		.attr('x', function(d){ return radius(d.rad); })
		.text(function(d){
			return d.name+ " ("+d.xValue+","+d.yValue+")";
	
		});
	
	
	    
    // label for x-axis
    svg.append('text')
		.attr("transform","translate("+
			(width/2)+","+(height+margin.top+10)+")")
		.attr('text-anchor', 'middle')
		.attr('class', 'label')
		.text('Size @ Birth');


	    
    svg.append('text')
    		.attr("transform","rotate(-90)")
		.attr("y",0-margin.left)
		.attr("x",0-(height/2))
		.attr("dy","1em")
		.attr('text-anchor', 'middle')
		.attr('class', 'label')
		.text('Duration');
	
	    
    //draw legend    
    var legend = svg.selectAll('legend')
		.data(color.domain())
		.enter().append('g')
		.attr('class', 'legend')
		.attr('transform', function(d,i){ return 'translate(0,' + i * 20 + ')'; });
	
	    
    //draw legend colored rects
    legend.append('rect')
		.attr('x', width)
		.attr('width', 18)
		.attr('height', 18)
		.style('fill', function(d){return color(d)});

    //add text to legend
    legend.append('text')
    		.attr('x', width - 6)
		.attr('y', 9)
		.attr('dy', '.35em')
		.style('text-anchor', 'end')
		.text(function(d){ 
		    	return d;
		    });
		   
    legend.on('click', function(type){
		d3.selectAll('.bubble')
			.style('opacity', 0.15)
			.filter(function(d){
				return getActivityClass(d.col) == type;
			})
			.style('opacity', 1);
	    
    });
	    
	    
}




//Draws the Inverse Gamma pattern   
function drawInvGammaPattern(data){
	
    try{
	//alert(data);
	var arr2 = JSON.parse(data); 
	console.log(arr2);
    }catch (e){
	alert(e);
    }
    
    	var margin = {top:30, right:30, bottom:50, left:70},
    		width = 800 - margin.left - margin.right,
    		height = 400 - margin.top - margin.bottom;
    
	    
	//add graph canvas to the jsp 
	var svg2 = d3.select('#scatter2')
		.append('svg')
		.attr('width', width+margin.right+margin.left)
		.attr('height', height + margin.top + margin.bottom)
		.append('g')
		.attr('transform', 'translate(' + margin.left + ',' + margin.top + ')');
	

	
	//setup radius
	var radius = d3.scaleSqrt().range([2,5]);

	//add domain to avoid overlapping
	var xMax = d3.max(arr2,(d)=>d.xValue);
	var xScale = d3.scaleLinear().range([0,width-margin.right])
	.domain([1,xMax])
		.nice();
	var xAxis = d3.axisBottom().scale(xScale).ticks(2);
	
	var yScale = d3.scaleLinear().range([height,0])
		.domain([0,d3.max(arr2, (d)=>d.yValue)])
		.nice();
	var yAxis = d3.axisLeft().scale(yScale);
	
	
	radius.domain(d3.extent(arr2,function(d){
		
		return d.rad;
		
	})).nice();
	

	
	  
	//setup fill color
	
//	var activity = [];
//	
//	arr2.forEach(function(d,i){
//		    
//	    activity[i] = getActivityClass(d.col);
//
//	    
//	});
	    
	    
	    
	//var colors = ["rgb(0,0,255)","rgb(0,255,0)","rgb(255,0,0)"];
	       
	var color = d3.scaleOrdinal(d3.schemeCategory10)
		.domain(arr2.map(function(d){return getActivityClass(d.col);}));
	
	// adding axes is also simpler now, just translate x-axis to (0,height) and it's already defined to be a bottom axis. 
	svg2.append('g')
		.attr('transform', 'translate(0,' + height + ')')
		.attr('class', 'x axis')
		.call(xAxis);

	// y-axis is translated to (0,0)
	svg2.append('g')
		.attr('transform', 'translate(0,0)')
		.attr('class', 'y axis')
		.call(yAxis);
	
	//draw bubbles
	var bubble = svg2.selectAll('.bubble2')
		.data(arr2)
		.enter().append('circle')
		.attr('class', 'bubble2')
		.attr('cx', function(d){return xScale(d.xValue);})
		.attr('cy', function(d){ return yScale(d.yValue);})
		.attr('r', function(d){ return radius(d.rad);})
		.style('fill', function(d,i){ return color(getActivityClass(d.col));});
	
	bubble.append('title')
		.attr('x', function(d){ return radius(d.rad); })
		.text(function(d){
			return d.name+ " ("+d.xValue+","+d.yValue+")";
	});
	
	
	//Labels for x and y axes
	svg2.append('text')

		.attr("transform","translate("+
			(width/2)+","+(height+margin.top+10)+")")
		.attr('text-anchor', 'middle')
		.attr('class', 'label')
		.text('Duration');


	svg2.append('text')
		.attr("transform","rotate(-90)")
		.attr("y",0-margin.left)
		.attr("x",0-(height/2))
		.attr("dy","1em")
		.attr('text-anchor', 'middle')
		.attr('class', 'label')
		.text('Sum(updates)');
	
	//draw legend
	var legend = svg2.selectAll('legend')
		.data(color.domain())
		.enter().append('g')
		.attr('class', 'legend')
		.attr('transform', function(d,i){ return 'translate(0,' + i * 20 + ')'; });
	
	//draw legend colored rects
	legend.append('rect')
		.attr('x', width)
		.attr('width', 18)
		.attr('height', 18)
		.style('fill', function(d){return color(d)});

	// add text to the legend elements.
	legend.append('text')
		.attr('x', width - 6)
		.attr('y', 9)
		.attr('dy', '.35em')
		.style('text-anchor', 'end')
		.text(function(d){ 
		    	return d;
		    });
	
	legend.on('click', function(type){
		d3.selectAll('.bubble2')
			.style('opacity', 0.15)
			.filter(function(d){
				return getActivityClass(d.col) == type;
			})
			.style('opacity', 1);
	});
	    
}




//Draws the Commet pattern   
function drawCommetPattern(data){
	
    try{
	//alert(data);
	var arr3 = JSON.parse(data); 
	console.log(arr3);
    }catch (e){
	alert(e);
    }
    
    	
    var margin = {top:30, right:30, bottom:50, left:70},
    		width = 800 - margin.left - margin.right,
    		height = 400 - margin.top - margin.bottom;
    
	    
	
    //add graph canvas to the jsp 
    var svg3 = d3.select('#scatter3')
		.append('svg')
		.attr('width', width+margin.right+margin.left)
		.attr('height', height + margin.top + margin.bottom)
		.append('g')
		.attr('transform', 'translate(' + margin.left + ',' + margin.top + ')');
	

	
	
    //setup radius
    var radius = d3.scaleSqrt().range([2,5]);

	
    //add domain to avoid overlapping
	
    var xMax = d3.max(arr3,(d)=>d.xValue);
	
    var xScale = d3.scaleLinear().range([0,width-margin.right])
	.domain([1,xMax])
		.nice();
	
    var xAxis = d3.axisBottom().scale(xScale).ticks(4);
	
	
    var yScale = d3.scaleLinear().range([height,0])
		.domain([0,d3.max(arr3, (d)=>d.yValue)])
		.nice();
	
    var yAxis = d3.axisLeft().scale(yScale);
	
	
	
    radius.domain(d3.extent(arr3,function(d){
		
		
	return d.rad;
		
	
    })).nice();
	

	

    //setup fill color
//    var activity = [];
//    arr3.forEach(function(d,i){
//
//	    activity[i] = getActivityClass(d.col);
//
//    });
    
    
    var color = d3.scaleOrdinal(d3.schemeCategory10)
	.domain(arr3.map(function(d){return getActivityClass(d.col);}));
	
	
	
    svg3.append('g')
		.attr('transform', 'translate(0,' + height + ')')
		.attr('class', 'x axis')
		.call(xAxis);

	
    // y-axis is translated to (0,0)
	
    svg3.append('g')
		.attr('transform', 'translate(0,0)')
		.attr('class', 'y axis')
		.call(yAxis);
	
	
    //draw bubbles
	
    var bubble = svg3.selectAll('.bubble3')
		.data(arr3)
		.enter().append('circle')
		.attr('class', 'bubble3')
		.attr('cx', function(d){return xScale(d.xValue);})
		.attr('cy', function(d){ return yScale(d.yValue);})
		.attr('r', function(d){ return radius(d.rad);})
		.style('fill', function(d,i){ return color(getActivityClass(d.col));});
	
	
    bubble.append('title')
		.attr('x', function(d){ return radius(d.rad); })
		.text(function(d){
			return d.name+ " ("+d.xValue+","+d.yValue+")";
	
		});
	
	
	
    //Labels for x and y axes
    svg3.append('text')

		.attr("transform","translate("+
			(width/2)+","+(height+margin.top+10)+")")
		.attr('text-anchor', 'middle')
		.attr('class', 'label')
		.text('Size@Birth');


	
    svg3.append('text')
		.attr("transform","rotate(-90)")
		.attr("y",0-margin.left)
		.attr("x",0-(height/2))
		.attr("dy","1em")
		.attr('text-anchor', 'middle')
		.attr('class', 'label')
		.text('Sum(updates)');
	
	
    //draw legend
	
    var legend = svg3.selectAll('legend')
		.data(color.domain())
		.enter().append('g')
		.attr('class', 'legend')
		.attr('transform', function(d,i){ return 'translate(0,' + i * 20 + ')'; });
	
	
    //draw legend colored rects
	
    legend.append('rect')
		.attr('x', width)
		.attr('width', 18)
		.attr('height', 18)
		.style('fill', function(d){return color(d)});

	
    // add text to the legend elements.
	
	
    legend.append('text')
		.attr('x', width - 6)
		.attr('y', 9)
		.attr('dy', '.35em')
		.style('text-anchor', 'end')
		.text(function(d){
		    	return d;
		    });
	
	
    // d3 has a filter fnction similar to filter function in JS. Here it is used to filter d3 components.
    legend.on('click', function(type){
		d3.selectAll('.bubble3')
			.style('opacity', 0.15)
			.filter(function(d){
				return getActivityClass(d.col) == type;
			})
			.style('opacity', 1);
	});
	    
}





//Draws the Empty Triangle pattern   
function drawEmptyTrianglePattern(data){
	
    try{
	//alert(data);
	var arr4 = JSON.parse(data); 
	console.log(arr4);
    }catch (e){
	alert(e);
    }
    
    	
    var margin = {top:30, right:30, bottom:50, left:70},
    		width = 800 - margin.left - margin.right,
    		height = 400 - margin.top - margin.bottom;
    
	    
	
    //add graph canvas to the jsp 
    var svg4 = d3.select('#scatter4')
		.append('svg')
		.attr('width', width+margin.right+margin.left)
		.attr('height', height + margin.top + margin.bottom)
		.append('g')
		.attr('transform', 'translate(' + margin.left + ',' + margin.top + ')');
	

	
	
    //setup radius
    var radius = d3.scaleSqrt().range([2,5]);

	
    //add domain to avoid overlapping
	
    var xMax = d3.max(arr4,(d)=>d.xValue);
	
    var xScale = d3.scaleLinear().range([0,width-margin.right])
	.domain([1,xMax])
		.nice();
	
    var xAxis = d3.axisBottom().scale(xScale).ticks(4);
	
	
    var yScale = d3.scaleLinear().range([height,0])
		.domain([0,d3.max(arr4, (d)=>d.yValue)])
		.nice();
	
    var yAxis = d3.axisLeft().scale(yScale);
	
	
	
    radius.domain(d3.extent(arr4,function(d){
		
		
	return d.rad;
		
	
    })).nice();
	

	
    //setup fill color
	
    //setup fill color
//    var activity = [];
//    arr4.forEach(function(d,i){
//
//	    activity[i] = getActivityClass(d.col);
//
//    });
    
    
    var color = d3.scaleOrdinal(d3.schemeCategory10)
	.domain(arr4.map(function(d){return getActivityClass(d.col);}));
	
	
	
    svg4.append('g')
		.attr('transform', 'translate(0,' + height + ')')
		.attr('class', 'x axis')
		.call(xAxis);

	
    // y-axis is translated to (0,0)
	
    svg4.append('g')
		.attr('transform', 'translate(0,0)')
		.attr('class', 'y axis')
		.call(yAxis);
	
	
    //draw bubbles
    var bubble = svg4.selectAll('.bubble4')
		.data(arr4)
		.enter().append('circle')
		.attr('class', 'bubble4')
		.attr('cx', function(d){return xScale(d.xValue);})
		.attr('cy', function(d){ return yScale(d.yValue);})
		.attr('r', function(d){ return radius(d.rad);})
		.style('fill', function(d,i){ return color(getActivityClass(d.col));});
	
	
    bubble.append('title')
		.attr('x', function(d){ return radius(d.rad); })
		.text(function(d){
			return d.name+ " ("+d.xValue+","+d.yValue+")";
	
		});
	
	
	
    //Labels for x and y axes
    svg4.append('text')

		.attr("transform","translate("+
			(width/2)+","+(height+margin.top+10)+")")
		.attr('text-anchor', 'middle')
		.attr('class', 'label')
		.text('Birth Version');


	
    svg4.append('text')
		.attr("transform","rotate(-90)")
		.attr("y",0-margin.left)
		.attr("x",0-(height/2))
		.attr("dy","1em")
		.attr('text-anchor', 'middle')
		.attr('class', 'label')
		.text('Duration');
	
	
    //draw legend
    var legend = svg4.selectAll('legend')
		.data(color.domain())
		.enter().append('g')
		.attr('class', 'legend')
		.attr('transform', function(d,i){ return 'translate(0,' + i * 20 + ')'; });
	
	
    //draw legend colored rects
    legend.append('rect')
		.attr('x', width)
		.attr('width', 18)
		.attr('height', 18)
		.style('fill', function(d){return color(d)});

	
    // add text to the legend elements.
    legend.append('text')
		.attr('x', width - 6)
		.attr('y', 9)
		.attr('dy', '.35em')
		.style('text-anchor', 'end')
		.text(function(d){ 
		    	return d;
		    });
	
	
  legend.on('click', function(type){
		d3.selectAll('.bubble4')
			.style('opacity', 0.15)
			.filter(function(d){
				return getActivityClass(d.col) == type;
			})
			.style('opacity', 1);
	
  });
	    
}

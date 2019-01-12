var SIMPLE_LINE = SIMPLE_LINE || (function(){
	
	var _args={};
	
	return {
	    
	    init:function(Args){
		
		_args = Args;
		
	    },
	        
	    drawLineChart:function(){
			    	
		var margin1 = {top:40, right:50, bottom:50, left:50},
		width = 800 - margin1.left - margin1.right,
		height = 500 - margin1.top - margin1.bottom;
		
		var svg = d3.select(".chart-area").append('svg') 
			.attr("width", width + margin1.left + margin1.right)
			.attr("height", height + margin1.top + margin1.bottom)
			.append("g")
			.attr("transform",
				"translate("+margin1.left + " ," + margin1.top+")");
				
		try{
		    var arr = JSON.parse(_args);
		}catch(e){
		    alert(e);
		}

		var x = d3.scalePoint()
				.range([0,width]);
		
		x.domain(
			arr.map(d=> { 
			    return d.name;
		}));
		
		var xAxis = d3.axisBottom()
				.scale(x)
				.tickValues(x.domain().filter(function(d,i){ return !(i%10)}));
		
		var y = d3.scaleLinear()
				.range([height,0]);
		
		var y1 = d3.scaleLinear()
				.range([height,0]);
		

		
		y.domain([0, d3.max(arr, function(d) { return Math.max(d.tables); })]);
		y1.domain([0, d3.max(arr, function(d) { return Math.max(d.keys); })]);
		
		
		var valueline = d3.line()
				.x(function(d){return x(d.name);})
				.y(function(d){return y(d.tables);})
		
		var valueline1 = d3.line()
				.x(function(d){return x(d.name);})
				.y(function(d){return y1(d.keys);})
			
		arr.forEach(function(d){

		    d.name = d.name;
		    d.tables = +d.tables;
		    d.keys = +d.keys;

		});


		svg.append("path")
			.data([arr])
			.attr("class","line")
			.attr("d",valueline);
		
		svg.append("path")
			.data([arr])
			.attr("class","line1")
			.attr("d",valueline1);
		
		svg.append("g")
			.attr("transform","translate(0,"+height+")")
			.style("font", "12px times")
			.call(xAxis)
			.selectAll("text")
			.attr("dx", "-1.1em")
			.attr("dy", "1.2em")
			.attr("transform", "rotate(-30)");
		
		// text label for the x axis
		svg.append("text")             
		      .attr("transform",
		            "translate(" + (width/2) + " ," + 
		                           (height + margin1.top+10) + ")")
		      .style("text-anchor", "middle")
		      .text("Versions");

		svg.append("g")
			.attr("class","axisBlue")
			.call(d3.axisLeft(y));
		
		// text label for the left y axis
		svg.append("text")
		      .attr("transform", "rotate(-90)")
		      .attr("y", 0 - margin1.left)
		      .attr("x",0 - (height / 2))
		      .attr("dy", "1em")
		      .style("text-anchor", "middle")
		      .text("#Tables");
		
		// text label for the right y axis
		svg.append("g")
			.attr("class","axisRed")
			.attr("transform", "translate( " + width + ", 0 )")
			.call(d3.axisRight(y1));
		
		svg.append("text")
		      .attr("transform", "rotate(-90)")
		      .attr("y", width+25)
		      .attr("x",0 - (height / 2))
		      .attr("dy", "1em")
		      .style("text-anchor", "middle")
		      .text("#Keys");
		 
		//add a title
		svg.append("text")
		      .attr("x", (width / 2))				
		      .attr("y", 0 - (margin1.top / 2))
		      .attr("text-anchor", "middle")	
		      .style("font-size", "20px") 
		      .style("text-decoration", "underline") 	
		      .text("Evolution of Tables and Foreign Keys");


	    }
	};
	
    }()); 
	

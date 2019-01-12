$(document).ready(function(){
    
    	drawDiachronicGraph();

	
	$("#groupId").change(function(){
	    
	    $("#group-form").prop('action','');
	    $("#group-form").prop('action','/visualizer');
	    $("#group-form").submit();
	    drawDiachronicGraph();
	    
	});
	
	$("#radiusId").change(function(){
	    
	    $("#group-form").prop('action','');
	    $("#group-form").prop('action','/visualizer');
	    $("#group-form").submit();
	    drawDiachronicGraph();
	    
	});
	
	
	$("#versionId").change(function(){
	    
	    $("#group-form").prop('action','');
	    $("#group-form").prop('action','/visualizer');
	    $("#group-form").submit();
	    drawDiachronicGraph();
	    
	});
	
	
	function drawDiachronicGraph(){
		
	    var nodes = {};
		
	    nodes = $("#nod").val();
		
	    var links = {};
	    links = $("#lin").val();

	    drawDG(nodes,links);
	    
	}
	
    
});


function drawDG(n,l){

    
    try{
	var nodes = JSON.parse(n); 
	console.log(nodes);
    }catch (e){
	alert(e);
    }
    
    
    try{
	var links = JSON.parse(l); 
	console.log(links);
    }catch (e1){
	alert(e1);
    }
    
  
    var margin = {top:30, right:30, bottom:30, left:30},
    	
    	clientWidth = window.innerWidth
    	||document.documentElement.clientWidth
    	||docucument.body.clientWidth,
    	
    	clientHeight =  window.innerHeight
    	||document.documentElement.clientHeight
    	||docucument.body.clientHeight,
    	
    	width = clientWidth - margin.left - margin.right,
    	height = clientHeight - margin.top - margin.bottom;

    
    var svg = d3.select('#scatterDG')
    		.append('svg')
    		.attr('width','100%')
    		.attr('height','100%')
    		.attr('viewbox',"0 0 100 100")
    		.attr('preserveAspectRatio',"xMidYMid meet")
    		.append('g');
    
    var color = d3.scaleOrdinal(d3.schemeCategory20)
    		.domain(nodes.map(function(d){return d.groupLabel}));
    
    var groups = nodes.map(function(d){return d.groupLabel;});
    
    var simulation = d3.forceSimulation()
    .force("link", d3.forceLink().id(function(d) {return d.id;}).strength(0.5))
    .force("charge", d3.forceManyBody())
    .force("collision",d3.forceCollide().radius(function(d){
	return radius(d.radius);
    }))
    .force("center", d3.forceCenter(width/2,height/2));
    
    simulation.force("forceX", d3.forceX(width/2).strength(function(d) { 
		return hasLinks(d,links) ? 0.0 : 0.5; }))
	      .force("forceY", d3.forceY(3*height/4).strength(function(d) { 
		return hasLinks(d,links) ? 0.0 : 0.5; }));
    
    
    function hasLinks(d, links) {
	   
	var isLinked = false;
		
	links.forEach(function(l) { 
	
	    if (l.source.id == d.id) {
		
		isLinked = true;
		
	    }
		
	})
	
	return isLinked;
	
    }
    
    
    
    //Tooltip for circles
    var tooltipDiv = d3.select("body").append("div")	
        .attr("class", "tooltip")
        .style("opacity", 0);
    
    var link = svg.append("g")
    .attr("class", "links")
    .selectAll("line")
    .data(links)
    .enter().append("line")
    .attr("stroke-width", function(d) { return Math.sqrt(d.value); });


    var node = svg.append("g")
    .attr("class", "nodes")
    .selectAll("g")
    .data(nodes)
    .enter().append("g");
  

    var radius = d3.scaleSqrt().range([2,4]);
    
    var circles = node.append('circle')
    	.attr('r', function(d){return radius(d.radius)})
    	.attr('fill', function(d) {return color(d.groupLabel);})
    	.on('mouseover',mouseOver)
    	.on('mouseout',mouseOut)
    	.call(d3.drag()
        .on('start', dragstarted)
        .on('drag', dragged)
        .on('end', dragended));

    var labels = node.append("text")
    	.text(function(d) {
    	    return d.id;
    	})
    	.attr('x', 10)
    	.attr('y', 3);

    node.append('title')
    	.text(function(d) { return d.id; });

    simulation
    	.nodes(nodes)
    	.on('tick', ticked);

    simulation.force('link')
    .links(links);

  
    d3.select("input[type=range]")
    .on("input", inputted);

  
    
    function inputted() {
	  simulation.force("link").strength(+this.value);
	  simulation.force("forceX").strength(+this.value);
	  simulation.force("forceY").strength(+this.value);
	  simulation.alpha(1).restart();
	
    }
    
    
    function ticked() {
	link.attr("x1", function(d) { return d.source.x; })
	.attr("y1", function(d) { return d.source.y; })
	.attr("x2", function(d) { return d.target.x; })
	.attr("y2", function(d) { return d.target.y; });

	node.attr("transform", function(d) {
	    return "translate(" + d.x + "," + d.y + ")";
	})
    }
    
    function dragstarted(d) {
	  if (!d3.event.active) simulation.alphaTarget(0.3).restart();
	  d.fx = d.x;
	  d.fy = d.y;
	}

	
    function dragged(d) {
	  d.fx = d3.event.x;
	  d.fy = d3.event.y;
	
    }

	
    function dragended(d) {
	  if (!d3.event.active) simulation.alphaTarget(0);
	  d.fx = null;
	  d.fy = null;
	
    }
    
    
    function mouseOver(d){
	
	tooltipDiv.transition()
	   .duration(200)
	   .style('opacity',0.9);
	
	tooltipDiv.html("Table Name: "+d.id+"<br/>"+
		"Group id: "+d.group+"<br/>"+
		"Group: "+d.groupLabel+"<br/>"+
		"Radius: "+d.radius)
		.style("left", (d3.event.pageX) + "px")		
                .style("top", (d3.event.pageY - 90) + "px");
	
    }
    
    
    function mouseOut(d){
	
	tooltipDiv.transition()
		.duration(500)
		.style("opacity",0);
	
    }
    
}
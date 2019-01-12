package gr.cs.uoi.daintiness.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import gr.cs.uoi.daintiness.core.ProjectServer;
import gr.cs.uoi.daintiness.core.ProjectServerFactory;
import gr.cs.uoi.daintiness.core.StatisticsServer;
import gr.cs.uoi.daintiness.core.StatisticsServerFactory;


/**
 * Receives requests related to visualization functionalities.
 * @author KD
 * @since 2018-12-02
 * @version 1.0
 */
@WebServlet(name="Visualizer",
urlPatterns= {"/visualizer"})
public class VisualizationServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private ProjectServer modelMngr;
	private ProjectServerFactory pFactory;
	
	private StatisticsServer stServer;
	private StatisticsServerFactory stFactory;
	
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	
		String par1 = "param1";
		String parName = "patt";
		String groupId = "0";
		String radiusId = "0";
		String versionId = "";
		
		if (request.getParameter(par1)!=null) {
			parName = request.getParameter(par1);
		}else
			System.err.println("Parameter of the request is null!");
		
		
		pFactory = new ProjectServerFactory(); 
		this.modelMngr = pFactory.createProjectServer(); 
				
		stFactory = new StatisticsServerFactory();
		stServer = stFactory.createStatisticsServer();
		
	
		if (parName.equals("patt")) {
			
			//Gamma Pattern(stats n data)
			request.setAttribute("gammaStats",stServer.createGammaPatternStats(modelMngr.getNodes(),
					modelMngr.getVersionsNum()));
			request.setAttribute("gammaValues",modelMngr.convertToJson(modelMngr.setBubblesValues(
							stServer.createGammaPatternData(modelMngr.getNodes())
						)));

			//Inverse Gamma Pattern(stats n data)
			request.setAttribute("invGammaStats",stServer.createInvGammaPatternStats(modelMngr.getNodes(),
					modelMngr.getVersionsNum()));
			request.setAttribute("invGammaValues",modelMngr.convertToJson(modelMngr.setBubblesValues(
							stServer.createInvGammaPatternData(modelMngr.getNodes())
					)));

			//Commet Pattern(stats n data)
			request.setAttribute("commetStats",stServer.createCommetPatternStats(modelMngr.getNodes()));
			request.setAttribute("commetValues",modelMngr.convertToJson(modelMngr.setBubblesValues(
							stServer.createCommetPatternData(modelMngr.getNodes())
					)));

			//Empty Triangle Pattern(stats n data)
			request.setAttribute("emptyTriangleStats",stServer.createEmptyTrianglePatternStats(
					modelMngr.getNodes(),modelMngr.getVersionsNum()));
			request.setAttribute("triangleValues",modelMngr.convertToJson(modelMngr.setBubblesValues(
							stServer.createEmptyTrianglePatternData(modelMngr.getNodes())
					)));

			request.getRequestDispatcher("/visualize.jsp").forward(request, response);
		
		}else if (parName.equals("dg")) {
			
			if (request.getParameter("groupId")!=null)
				groupId = request.getParameter("groupId");
			else
				System.err.println("groupId parameter is null");
			
			if (request.getParameter("radiusId")!=null)
				radiusId = request.getParameter("radiusId");
			else
				System.err.println("radiusId parameter is null");
			
			if (request.getParameterMap().containsKey("versionId"))
				versionId = request.getParameter("versionId");
			
			String nodes = getDiachronicGraphNodes(groupId,radiusId,versionId);
			
			request.setAttribute("DGNodes",nodes);
			
			String links = getDiachronicGraphEdges(versionId);

			request.setAttribute("DGLinks",links);
			
			request.setAttribute("gId",groupId);

			request.setAttribute("rId",radiusId);
			
			request.setAttribute("vNum", String.valueOf(modelMngr.getVersionsNum()));
			
			request.setAttribute("cVersion", versionId);
			
			request.getRequestDispatcher("/visualizeDG.jsp").forward(request, response);
			
		}
			
	}
	
	
	/**
	 * Creates objects that represent the tables of the DB.
	 * @param groupId - defines how nodes are grouped. 
	 * @param radiusId - defines the radius used in nodes' visualization.
	 * @param versionId - used to create nodes of the individual DB versions.
	 * @return a list of objects of {@link gr.cs.uoi.daintiness.model.Node} class in JSON format.
	 * @since 2018-12-03
	 */
	private String getDiachronicGraphNodes(String groupId,String radiusId,String versionId) {
		
		if (versionId.isEmpty()||versionId.equals(null))
			modelMngr.setNodes(groupId,radiusId);
		else 
			modelMngr.setVersionNodes(groupId,radiusId,versionId);
			
		return modelMngr.getNodesInJson();
		
	}
	
	
	
	/**
	 * Creates objects that represent the foreign key constraints of the DB.
	 * @param versionId - the id of individual versions.
	 * @return a list of objects of {@link gr.cs.uoi.daintiness.model.Link} class in JSON format.
	 * @since 2018-12-03
	 */
	private String getDiachronicGraphEdges(String versionId) {
		
		if (versionId.isEmpty()||versionId.equals(null)||versionId.equals("0"))
			modelMngr.setLinks();
		else
			modelMngr.setVersionLinks(versionId);
		
		String links = modelMngr.getLinksInJson();

		return links;
		
	}
	
	
	// Redirect POST request to GET request.
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
			doGet(request, response);
		
	}
	
	
}
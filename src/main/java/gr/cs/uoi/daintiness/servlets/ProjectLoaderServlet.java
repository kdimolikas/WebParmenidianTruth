package gr.cs.uoi.daintiness.servlets;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import gr.cs.uoi.daintiness.core.ProjectServer;
import gr.cs.uoi.daintiness.core.ProjectServerFactory;
import gr.cs.uoi.daintiness.core.StatisticsServer;
import gr.cs.uoi.daintiness.core.StatisticsServerFactory;

/**
 * Receives requests for loading a parmenidian project sending back responses
 * related to this project.
 * @author KD
 * @since 2018-12-12
 * @version 1.0
 */

@WebServlet(name="ProjectLoader",
			urlPatterns= {"/loader"})
@MultipartConfig
public class ProjectLoaderServlet extends HttpServlet {


	private static final long serialVersionUID = 1L;
	private static final String PROJECT_FOLDER = "resources/projects";

	private ProjectServer prjServer;
	private ProjectServerFactory prjServerFactory;
	
	private StatisticsServer stServer;
	private StatisticsServerFactory stFactory;
	
	
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		String path = PROJECT_FOLDER;
		File dir = null;
		List<String> projectsNames = new ArrayList<String>();
		
		this.prjServerFactory = new ProjectServerFactory();
		this.prjServer = prjServerFactory.createProjectServer();
		
		String projectId = null;
		
		stFactory = new StatisticsServerFactory();
		this.stServer = this.stFactory.createStatisticsServer();
		
		
		if (!new File(path).exists()) {
    		 new File(path).mkdirs();
    		 
		}
		
		dir = new File(path) ;
		File[] fileList = dir.listFiles();
		
		
		for(File f:fileList)
			if (f.isDirectory())
				projectsNames.add(f.getName());

		//redirecting to upload page when the user load a project from server
		if (request.getParameterMap().containsKey("optradio")) {
			if (!request.getParameter("optradio").equals(null) &&
					!request.getParameter("optradio").isEmpty()) {

				projectId = request.getParameter("optradio");
				String projectName = projectsNames.get(Integer.valueOf(projectId));
				
				//Parse xml and load data
				this.prjServer.parseXmlDoc(new File(PROJECT_FOLDER.concat("/").concat(projectName)));

				
				request.setAttribute("stats",stServer.createOverallStats(
						this.prjServer.getOverallStats()));

				request.setAttribute("statsOver",this.stServer.createBirthVersionStats(
						this.prjServer.getBirthVersionStats()));

				request.setAttribute("schemas",prjServer.getVersionsInJson());
				request.setAttribute("hideAttr",'1');
				request.getRequestDispatcher("/upload.jsp").forward(request, response);
				
			}
		
		//redirecting to upload page when user selects different stats 
		}else if(request.getParameterMap().containsKey("statsId")) {
				
			String statsId = request.getParameter("statsId");
			
			if (statsId.equals("1"))
			
				request.setAttribute("statsOver",this.stServer.createBirthVersionStats(
							this.prjServer.getBirthVersionStats()));
				
			else if (statsId.equals("2"))
			
				request.setAttribute("statsOver",this.stServer.createSurvivalStats(
							this.prjServer.getSurvivalStats()));
			
			else if (statsId.equals("3"))
				
				request.setAttribute("statsOver",this.stServer.createDurationStats(
							this.prjServer.getDurationStats()));
			
			else if (statsId.equals("4"))
				
				request.setAttribute("statsOver",this.stServer.createActivityStats(
							this.prjServer.getActivityStats()));
				
			request.setAttribute("stats",stServer.createOverallStats(
					this.prjServer.getOverallStats()));
			
			request.setAttribute("schemas",prjServer.getVersionsInJson());
			
			request.setAttribute("hideAttr",'1');
			
			request.getRequestDispatcher("/upload.jsp").forward(request, response);
			

		//redirecting to load page
		}else {
				request.setAttribute("projects",projectsNames);
				request.getRequestDispatcher("/load.jsp").forward(request, response);
		}

	}
	
	
	// Redirect POST request to GET request.
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		doGet(request, response);
	}
	
	

}
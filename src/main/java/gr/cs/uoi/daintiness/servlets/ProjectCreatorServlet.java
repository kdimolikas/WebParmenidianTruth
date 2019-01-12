package gr.cs.uoi.daintiness.servlets;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.commons.io.FileUtils;



import gr.cs.uoi.daintiness.core.ParmenidianServer;
import gr.cs.uoi.daintiness.core.ParmenidianServerFactory;
import gr.cs.uoi.daintiness.core.ProjectServer;
import gr.cs.uoi.daintiness.core.ProjectServerFactory;
import gr.cs.uoi.daintiness.core.StatisticsServer;
import gr.cs.uoi.daintiness.core.StatisticsServerFactory;

/**
 * Receives requests for uploading DD files containing the schema evolution of a DB
 * sending back responses related to its evolution.
 * @author KD
 * @since 2018-11-08
 * @version 1.0
 */
@WebServlet(name="ProjectUploader",
			urlPatterns= {"/uploader"})
@MultipartConfig
public class ProjectCreatorServlet extends HttpServlet {

	private final static Logger LOGGER = 
            Logger.getLogger(ProjectCreatorServlet.class.getCanonicalName());
	private static final long serialVersionUID = 1L;
	private static final String TEMP_FOLDER = "resources/tmp";
	private static final String PROJECT_FOLDER = "resources/projects";
	
		
	private ParmenidianServer parmenidianServer;
	private ParmenidianServerFactory parmenidianFactory;
	
	private ProjectServer projectServer;
	private ProjectServerFactory projectFactory;
	
	private StatisticsServer stServer;
	private StatisticsServerFactory stFactory;
	
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		parmenidianFactory = new ParmenidianServerFactory();
		parmenidianServer = parmenidianFactory.createParmenidianServer();
		
		projectFactory = new ProjectServerFactory();
		projectServer = projectFactory.createProjectServer();
		
		stFactory = new StatisticsServerFactory();
		this.stServer = this.stFactory.createStatisticsServer();
		
		
		String path = TEMP_FOLDER;
		
				
		if (!request.getParameter("projectName").equals(null) &&
				!request.getParameter("projectName").isEmpty()) {
				path = PROJECT_FOLDER;
				path = path.concat("/"+request.getParameter("projectName"));
		}
		
		@SuppressWarnings("unused")
		File dir = null;
		
		if (!new File(path).exists())
    		new File(path).mkdirs();
		else
			FileUtils.cleanDirectory(new File(path));
		
		dir = new File(path);
		
	    List<Part> fileParts = request.getParts().stream().filter(part -> 
	    	"file".equals(part.getName())).collect(Collectors.toList());
	    
	    
	    for (Part fPart:fileParts) {
	    
	    	String fileName = Paths.get(fPart.getSubmittedFileName()).getFileName().toString();

	    	OutputStream out = null;
	    	InputStream filecontent = null;

	    	try {

	    		File output = new File(path + File.separator + fileName);

	    		out = new FileOutputStream(output);
	    		filecontent = fPart.getInputStream();

	    		int read = 0;
	    		final byte[] bytes = new byte[40000];

	    		while ((read = filecontent.read(bytes)) != -1) {
	    			out.write(bytes, 0, read);
	    		}
	    		System.out.println("New file " + fileName + " created at " + path);
	    		LOGGER.log(Level.INFO, "File {0} being uploaded to {1}", 
	    				new Object[]{fileName, path});
	    	} catch (FileNotFoundException fne) {
	    		System.out.println("You either did not specify a file to upload or are "
	    				+ "trying to upload a file to a protected or nonexistent "
	    				+ "location.");
	    		System.out.println("<br/> ERROR: " + fne.getMessage());

	    		LOGGER.log(Level.SEVERE, "Problems during file upload. Error: {0}", 
	    				new Object[]{fne.getMessage()});
	    	} finally {
	    		if (out != null) {
	    			out.close();
	    		}
	    		if (filecontent != null) {
	    			filecontent.close();
	    		}

	    	}
	    }
	    
		try {
			parmenidianServer.createProject(path);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		projectServer.createNodes();
		projectServer.createLinks();
		projectServer.createVersions();
		
		
		request.setAttribute("stats",stServer.createOverallStats(
				this.projectServer.getOverallStats()));
		request.setAttribute("statsOver",this.stServer.createBirthVersionStats(
				this.projectServer.getBirthVersionStats()));
		
		
		File modelFile = new File(path+"/model.xml");
		projectServer.createXmlWithVersions(modelFile);

		
		request.setAttribute("schemas",projectServer.getVersionsInJson());
		request.getRequestDispatcher("/upload.jsp").forward(request, response);

	}
	
	
	
	// Redirect POST request to GET request.
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		doGet(request, response);
	}
	
	
	
	@SuppressWarnings("unused")
	private String getSubmittedFileName(final Part part) {
		
		final String partHeader = part.getHeader("content-disposition");
	    LOGGER.log(Level.INFO, "Part Header = {0}", partHeader);
	    for (String content : part.getHeader("content-disposition").split(";")) {
	        if (content.trim().startsWith("filename")) {
	            return content.substring(
	                    content.indexOf('=') + 1).trim().replace("\"", "");
	        }
	    }
	    return null;
	}
	
}
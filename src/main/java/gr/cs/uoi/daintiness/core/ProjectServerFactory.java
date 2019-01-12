package gr.cs.uoi.daintiness.core;

/**
 * 
 * @author KD
 *
 */

public class ProjectServerFactory {
	
	
	public ProjectServer createProjectServer(){
		
		return ProjectProvider.getInstance();
		
	}
	

}

package gr.cs.uoi.daintiness.core;

import java.io.File;
import java.util.List;
import java.util.Map;

import gr.cs.uoi.daintiness.enums.ActivityStatus;
import gr.cs.uoi.daintiness.enums.BirthVersionLabel;
import gr.cs.uoi.daintiness.enums.DurationLabel;
import gr.cs.uoi.daintiness.enums.Labels;
import gr.cs.uoi.daintiness.enums.SurvivalStatus;
import gr.cs.uoi.daintiness.model.Link;
import gr.cs.uoi.daintiness.model.Node;

/**
 * Defines methods requested by {@link gr.cs.uoi.daintiness.servlets.ProjectCreatorServlet} and
 * {@link  gr.cs.uoi.daintiness.servlets.ProjectLoaderServlet} classes.
 * @author KD
 * @since 2018-12-15
 * @version 1.0
 */
public interface ProjectServer {
	
	
	//Project Creator
	public List<Link> getLinks();
	public void createNodes();
	public void createVersions();
	public Map<Labels, Integer> getOverallStats();
	public Map<BirthVersionLabel, Integer> getBirthVersionStats();
	public void createLinks();
	public void createXmlWithVersions(File dir);
	public void parseXmlDoc(File dir);
	public Map<SurvivalStatus, Integer> getSurvivalStats();
	public Map<DurationLabel, Integer> getDurationStats();
	public Map<ActivityStatus, Integer> getActivityStats();
	
	//Common
	public List<Bubble> setBubblesValues(Map<String,int[]> map);
	public void setNodes(String groupId,String radiusId);
	
	
	//Visualization
	public List<Node> getNodes();
	public String convertToJson(List<?> list);
	public String getVersionsInJson();
	public String getNodesInJson();
	public String getLinksInJson();
	public int getVersionsNum();
	public void setVersionNodes(String gId, String rId, String vId);
	public void setLinks();
	public void setVersionLinks(String vId);

	

}

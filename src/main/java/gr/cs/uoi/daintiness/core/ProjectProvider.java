package gr.cs.uoi.daintiness.core;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import gr.cs.uoi.daintiness.enums.ActivityStatus;
import gr.cs.uoi.daintiness.enums.BirthVersionLabel;
import gr.cs.uoi.daintiness.enums.DurationLabel;
import gr.cs.uoi.daintiness.enums.Labels;
import gr.cs.uoi.daintiness.enums.SurvivalStatus;
import gr.cs.uoi.daintiness.model.Link;
import gr.cs.uoi.daintiness.model.Model;
import gr.cs.uoi.daintiness.model.Node;
import gr.cs.uoi.daintiness.model.Version;
import gr.cs.uoi.daintiness.model.XmlProvider;
import model.DBVersion;
import model.ForeignKey;
import model.Table;

/**
 * Implements the methods provided by the {@link gr.cs.uoi.daintiness.core.ProjectServer} interface.
 * @author KD
 * @since 2018-12-14
 * @version 1.0
 */
public class ProjectProvider implements ProjectServer {
	
	private static ProjectProvider instance = null;
	
	private List<Node> nodes;

	private List<Node> groupedNodes;
	
	private List<Link> links;

	private List<Link> groupedLinks;
	
	private List<Version> versions;
	
	private ParmenidianServer parmServer;
	
	private ParmenidianServerFactory parmFactory;
	
	private Model model;
	
	private XmlProvider xmlManager;
	
	
	private StatisticsServer stServer;
	private StatisticsServerFactory stFactory;
	
	
	protected ProjectProvider() {
		
		nodes = new ArrayList<Node>();
		groupedNodes = new ArrayList<Node>();
		links = new ArrayList<Link>();
		groupedLinks = new ArrayList<Link>();
		versions = new ArrayList<Version>();
		this.parmFactory = new ParmenidianServerFactory();
		
		this.stFactory = new StatisticsServerFactory();
		this.stServer = this.stFactory.createStatisticsServer();
		this.xmlManager = new XmlProvider();
		
	}
	
	public static ProjectProvider getInstance() {
		
		if (instance==null)
			instance = new ProjectProvider();

		return instance;
	}

	
	public void createLinks() {
		
		
		for (ForeignKey f:parmServer.getForeignKeys()) {
			
			Link l = new Link(f.getSourceTable(),f.getTargetTable());
			links.add(l);
			
		}
		
	}
	
	
	public void createNodes() {
		
		this.clear();
		
		parmServer = parmFactory.createParmenidianServer();
		
		for (Table t:parmServer.getTables()) {
			
			Node n = null;
							
			n = new Node(t.getTableName(),0);
						
			n.setDuration(t.getDuration());
			
			n.setBirthVersion(t.getBirthVersion());
			
			n.setSurvivorStatus(t.getSurvivorStatus());
			
			n.setChangesNum(t.getSumUpdates());
			
			n.setActivityStatus(stServer.getActivityLabel(t.getSumUpdates(), t.getATU()));
			
			n.setSizeAtBirth(t.getAttrsNumAtBirth());
			
			n.setATU();
			
			nodes.add(n);
			
		}
		
	}
	
	
	
	
	/**
	 * Load links from xml file.
	 */
	
	private void loadLinks() {
		
		
		this.links = xmlManager.getLinks();
		
	}
	
	public void setLinks() {
		
		
		for (Link l:this.links) {
			
			Link lk = new Link(l.getSourceNode(),l.getTargetNode());
			groupedLinks.add(lk);
			
		}
		
	}
	
	/**
	 * Created only for test purposes.
	 * @param lk - a list of links
	 */
	public void setGroupedLinks(List<Link> lk) {
		this.clearGroupedData();
		for (Link l:lk)
			this.groupedLinks.add(l);
		
	}
	
	public void setVersionLinks(String vId) {
		
		
		Version version = versions.get(Integer.valueOf(vId));
		
		for (Link fk:version.getVersionLinks()) {
			
			Link l = new Link(fk.getSourceNode(),fk.getTargetNode());
			this.groupedLinks.add(l);
		}
		
	}
	
	
	public List<Node> getNodes(){
		
		return this.nodes;
		
	}
	
	public List<Version> getVersions(){
		
		return this.versions;
	}
	
	
	public int getVersionsNum() {
		
		return this.versions.size();
	}
			
	

	public String convertToJson(List<?> list) {
		
		JsonConverter converter = new JsonConverter();
		return converter.convertListToJsonString(list);
		
	}
	
	
	@Override
	public Map<Labels, Integer> getOverallStats() {
		
		Map<Labels,Integer> stats = new TreeMap<Labels,Integer>();
		
		stats.put(Labels.TABLES, nodes.size());
		stats.put(Labels.KEYS, links.size());
		stats.put(Labels.VERSIONS, versions.size());
		
		return stats;
	}
	
	
	/**
	 * 
	 */
	@Override
	public Map<BirthVersionLabel, Integer> getBirthVersionStats() {

		Map<BirthVersionLabel,Integer> stats = new TreeMap<BirthVersionLabel,Integer>();
		
		double versionsNum = this.versions.size();
		
		
		stats.put(BirthVersionLabel.EARLY_BORN, 0);
		stats.put(BirthVersionLabel.MEDIUM_BORN, 0);
		stats.put(BirthVersionLabel.LATE_BORN, 0);
		
		for (Node n:nodes) {
			if((n.getBirthVersion()/versionsNum) < 0.33)
				stats.put(BirthVersionLabel.EARLY_BORN,stats.get(BirthVersionLabel.EARLY_BORN) + 1);
			else if(((n.getBirthVersion()/versionsNum) >= 0.33) &&
					((n.getBirthVersion()/versionsNum) <= 0.77))
				stats.put(BirthVersionLabel.MEDIUM_BORN,stats.get(BirthVersionLabel.MEDIUM_BORN) + 1);
			else 
				stats.put(BirthVersionLabel.LATE_BORN,stats.get(BirthVersionLabel.LATE_BORN) + 1);
			
		}
		
		return stats;
		
	}
	
	
	@Override
	public Map<SurvivalStatus, Integer> getSurvivalStats() {

		Map<SurvivalStatus,Integer> stats = new TreeMap<SurvivalStatus,Integer>();
		
		stats.put(SurvivalStatus.SURVIVOR, 0);
		stats.put(SurvivalStatus.NON_SURVIVOR, 0);
		
		for (Node n:nodes) {
			if(n.getSurvivorStatus() == SurvivalStatus.SURVIVOR.getValue())
				stats.put(SurvivalStatus.SURVIVOR,stats.get(SurvivalStatus.SURVIVOR) + 1);
			else 
				stats.put(SurvivalStatus.NON_SURVIVOR,stats.get(SurvivalStatus.NON_SURVIVOR) + 1);
			
		}
		
		return stats;
		
	}
	
	
	
	@Override
	public Map<DurationLabel, Integer> getDurationStats() {

		Map<DurationLabel,Integer> stats = new TreeMap<DurationLabel,Integer>();
		
		stats.put(DurationLabel.SHORT_LIVED, 0);
		stats.put(DurationLabel.MEDIUM_LIVED, 0);
		stats.put(DurationLabel.LONG_LIVED, 0);
		
		int versionsNum = versions.size();
		
		for (Node n:nodes) {
			if(stServer.getTableNormCategory(n.getDuration(), versionsNum) == 1)
				stats.put(DurationLabel.SHORT_LIVED,stats.get(DurationLabel.SHORT_LIVED) + 1);
			else if (stServer.getTableNormCategory(n.getDuration(), versionsNum) == 2)
				stats.put(DurationLabel.MEDIUM_LIVED,stats.get(DurationLabel.MEDIUM_LIVED) + 1);
			else
				stats.put(DurationLabel.LONG_LIVED,stats.get(DurationLabel.LONG_LIVED) + 1);
				
			
		}
		
		return stats;
		
	}
	
	
	@Override
	public Map<ActivityStatus, Integer> getActivityStats() {

		Map<ActivityStatus,Integer> stats = new TreeMap<ActivityStatus,Integer>();
		
		stats.put(ActivityStatus.RIGID, 0);
		stats.put(ActivityStatus.QUIET, 0);
		stats.put(ActivityStatus.ACTIVE, 0);
		
		for (Node n:nodes) {
			if(stServer.getActivityLabel(n.getChangesNum(), n.getATU()).equals(
					ActivityStatus.RIGID.toString()) )
				stats.put(ActivityStatus.RIGID,stats.get(ActivityStatus.RIGID) + 1);
			else if (stServer.getActivityLabel(n.getChangesNum(), n.getATU()).equals(
					ActivityStatus.QUIET.toString()))
				stats.put(ActivityStatus.QUIET,stats.get(ActivityStatus.QUIET) + 1);
			else
				stats.put(ActivityStatus.ACTIVE,stats.get(ActivityStatus.ACTIVE) + 1);
				
			
		}
		
		return stats;
		
	}
	
	
	
	/**
	 * Load nodes from xl file.
	 */
	
	private void loadNodes() {
		
		this.nodes = xmlManager.getNodes();
		
	}
	
	
	/**
	 * 
	 * @param groupId
	 * @param radiusId
	 * @since 2018-12-03
	 */
	public void setNodes(String groupId,String radiusId) {
		
		this.clearGroupedData();
		
		for (Node nd:this.nodes) {
			
			Node n = null;
			
			if (groupId.equals("0")) {
				n = new Node(nd.getNodeName(),nd.getBirthVersion());
				n.setLabel(stServer.getBirthVersionLabel(nd.getBirthVersion(),this.versions.size()));
			}else if (groupId.equals("1")) {
				n = new Node(nd.getNodeName(),stServer.getTableUpdateClass(nd.getChangesNum(),nd.getATU()));
				n.setLabel(stServer.getActivityLabel(nd.getChangesNum(), nd.getATU()));
			}else if (groupId.equals("2")) {
				n = new Node(nd.getNodeName(),nd.getSurvivorStatus());
				n.setLabel(stServer.getSurvivalLabel(nd.getSurvivorStatus()));
			}
				
			if (radiusId.equals("0"))
				n.setRadius(nd.getSizeAtBirth());
			else if (radiusId.equals("1"))
				n.setRadius(nd.getDuration());
			
			this.groupedNodes.add(n);
			
		}
		
	}
	
public void setVersionNodes(String gId, String rId, String vId) {
		

	this.clearGroupedData();
	
	Version version = versions.get(Integer.valueOf(vId));

	for (Node nd:version.getVersionNodes()) {

		Node n = null;

		if (gId.equals("0")) {
			n = new Node(nd.getNodeName(),nd.getBirthVersion());
			n.setLabel(stServer.getBirthVersionLabel(nd.getBirthVersion(),this.versions.size()));
		}else if (gId.equals("1")) {
			n = new Node(nd.getNodeName(),stServer.getTableUpdateClass(nd.getChangesNum(), nd.getATU()));
			n.setLabel(stServer.getActivityLabel(nd.getChangesNum(), nd.getATU()));
		}else if (gId.equals("2")) {
			n = new Node(nd.getNodeName(),nd.getSurvivorStatus());
			n.setLabel(stServer.getSurvivalLabel(nd.getSurvivorStatus()));
		}
		
		//Set radius
		if (rId.equals("0"))
			n.setRadius(nd.getSizeAtBirth());
		else if (rId.equals("1"))
			n.setRadius(nd.getDuration());

		groupedNodes.add(n);
	}
			
		
	}
		
	
	public String getVersionsInJson() {
		
		return this.convertToJson(this.versions);
		
	}
	
	public String getLinksInJson() {
		
		return this.convertToJson(this.groupedLinks);
	}
	
	public void createXmlWithVersions(File dir) {
		
		model = new Model(this.versions);
		xmlManager.createXmlDoc(model, dir);
	}
	
	
	public void parseXmlDoc(File dir) {
		
		
		this.clear();
		xmlManager.parseXmlDoc(dir);
		
		this.loadNodes();
		this.loadLinks();
		this.loadVersions();
		
		updateNodes();
		this.updateVersionNodes();
	}
	
	
	private void updateNodes() {
		
		for (Node n:nodes)
			stServer.getActivityLabel(n.getChangesNum(),n.getATU());
		
	}
	
	
	/**
	* Creates the objects used for creating scatter plots.
	* @param map - data used to create the list of Bubble objects.
	* @return a list with the values that we use to create scatter plots
	* @author KD
	* @since 2018-11-28
	*/
	public List<Bubble> setBubblesValues(Map<String,int[]> map) {
		
		List<Bubble> bubbles = new ArrayList<Bubble>();
				
		for (Map.Entry<String, int[]> entry: map.entrySet()) {
			
			bubbles.add(new Bubble(entry.getKey(),entry.getValue()[0],entry.getValue()[1],
					entry.getValue()[2],entry.getValue()[3]));
			
		}
		
		return bubbles;
	}
	
	private void clear() {
		
		this.nodes.clear();
		this.links.clear();
		this.versions.clear();
		
	}
	
	private void clearGroupedData() {
		
		this.groupedNodes.clear();
		this.groupedLinks.clear();
		
	}


	@Override
	public List<Link> getLinks() {
		
		return this.links;
	}


	/**
	 * Created only for test purposes.
	 * @return a list of links.
	 */
	public List<Link> getGroupedLinks(){
		return this.groupedLinks;
		
	}
	
	public void createVersions() {
		
		
		for (DBVersion v:parmServer.getVersions()) {
			
			Version vr = new Version(v.getVersion(),v.getVersionId());
			vr.setVersionNodes(v.getTables());
			vr.setVersionLinks(v.getVersionForeignKeys());
			versions.add(vr);
			
		}
		
		updateVersionNodes();
		
	}
	
	
	private Node getNodeWithName(String name) {
		
		for (Node n:nodes)
			if (name.equals(n.getNodeName()))
				return n;
		
		return null;
		
	}
	
	/**
	 * Updates the attrs of each version's nodes.
	 */
	private void updateVersionNodes() {
		
		for (Version v:versions) {
			for (Node n:v.getVersionNodes()) {
				
				Node nd = this.getNodeWithName(n.getNodeName());
				n.setBirthVersion(nd.getBirthVersion());
				n.setDuration(nd.getDuration());
				n.setSizeAtBirth(nd.getSizeAtBirth());
				n.setChangesNum(nd.getChangesNum());
				n.setSurvivorStatus(nd.getSurvivorStatus());
				n.setATU();
							
			}
			
			
		}
		
	}
	
	
	
	/**
	 * Load versions from xml file.
	 */
	private void loadVersions() {
		
		this.versions = xmlManager.getVersions();
		
	}
	
	
	@Override
	public String getNodesInJson() {

		return convertToJson(this.groupedNodes);
	}
	
	
}

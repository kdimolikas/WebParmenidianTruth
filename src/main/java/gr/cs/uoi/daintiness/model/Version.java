package gr.cs.uoi.daintiness.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;


import model.ForeignKey;
import model.Table;


public class Version {
	
	
	@XmlAttribute(name="versionName")
	private String name;
	
	@XmlAttribute(name="versionId")
	private int id;
	
	@SuppressWarnings("unused")
	private int tables;
	@SuppressWarnings("unused")
	private int keys;
	
	@XmlElementWrapper(name="nodes")
	@XmlElement(name="node")
	private List<Node> versionNodes;
	
	@XmlElementWrapper(name="links")
	@XmlElement(name="link")
	private List<Link> versionLinks;
	

	public Version() {
		versionNodes = new ArrayList<Node>();
		versionLinks = new ArrayList<Link>();
		
	}
	
	
	public Version(String aName,int anId) {
		
		this.name = aName;
		this.id = anId;
		versionNodes = new ArrayList<Node>();
		versionLinks = new ArrayList<Link>();

		
	}
	
	
	
	/**
	 * 
	 * @param tables
	 */
	public void setVersionNodes(List<Table> tables) {
		
		for (Table t:tables) {
			
			Node n = new Node(t.getTableName(),t.getDuration());
			
			this.versionNodes.add(n);
		}
		
		this.tables = this.versionNodes.size();
		
	}
	

	public void setNodes(List<Node> nodes) {
		
		this.versionNodes = nodes;
		
	}
	
	
	/**
	 * 
	 * @param keys
	 */
	public void setVersionLinks(List<ForeignKey> keys) {
		
		for (ForeignKey fk:keys) {
			
			Link l = new Link(fk.getSourceTable(),fk.getTargetTable());
			this.versionLinks.add(l);
			
		}
		
		this.keys = this.versionLinks.size();
		
	}
	
	@XmlAttribute(name="keysNum")
	public int getKeysNum() {
		
		return this.keys;
		
	}
	
	public void setKeysNum(int size) {
		
		
		this.keys = size;
		
	}
	
	
	
	@XmlAttribute(name="tablesNum")
	public int getTablesNum() {
		
		return this.tables;
		
	}
	
	public void setTablesNum(int size) {
		
		
		this.tables = size;
		
	}
	
	public List<Node> getVersionNodes(){
		
		return this.versionNodes;
	}
	
	public List<Link> getVersionLinks(){
		
		return this.versionLinks;
	}
	
	
	public int getVersionId() {
		
		return this.id;
	}

}

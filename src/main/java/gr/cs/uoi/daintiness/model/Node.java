package gr.cs.uoi.daintiness.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;



/**
 * Represents a table of a database.
 * @author KD
 * @since 2018-11-06
 * @version 1.0
 */
@XmlRootElement(name="node")
public class Node {
	
	private String id;//table's name
	private int group;
	private int birthVersion;
	private int survivor;
	private int duration;
	private int changes;
	private int size;
	
	private double atu;
	@SuppressWarnings("unused")
	private String activity;
	@SuppressWarnings("unused")
	private int atttr1;
	private int radius;
	@SuppressWarnings("unused")
	private String groupLabel;
	
	public Node() {
		
		this.group = 0;
		this.groupLabel = String.valueOf(this.group);

		
	}
	
	public Node(String anId, int aGroup) {
		
		this.id = anId;
		this.group = aGroup;
		this.atttr1 = 0;
		this.radius = 2;
		this.groupLabel = String.valueOf(aGroup);
		
	}
	
	

	
	public void setSurvivorStatus(int status) {
		
		this.survivor = status;
		
	}
	
	public void setActivityStatus(String label) {
		
		this.activity = label;
	}
	
	public void setAttr1(int size) {
		
		this.atttr1 = size;
	}
	
	
	public void setATU() {
		
		this.atu = this.changes/(double)this.duration;
	}
	
	public void setChangesNum(int ch) {
		
		this.changes = ch;
	}
	
	
	public void setSizeAtBirth(int s) {
		
		this.size = s;
	}
	
	public int getSizeAtBirth() {
		
		return this.size;
	}
	
	@XmlElement(name="totChanges")
	public int getChangesNum() {
		
		return this.changes;
	}
	
	public void setDuration(int dur) {
		
		this.duration = dur;
		
	}
	
	public void setLabel(String aLabel) {
		
		this.groupLabel = aLabel;
		
	}
	
	public void setRadius(int r) {
		
		this.radius = r;
	}

	
	public void setBirthVersion(int bv) {
		
		this.birthVersion = bv;
	}
	
	
	public double getATU() {
		
		return this.atu;
	}
	
	
	public int getBirthVersion() {
		
		return this.birthVersion;
	}
	
	
	public String getGroupLabel() {
		
		return this.groupLabel;
		
	}
	
	@XmlElement(name="name")
	public String getNodeName() {
		
		return this.id;
	}
	
	public void setNodeName(String aName) {
		
		this.id = aName;
	}
	
	public int getNodeGroup() {
		
		return this.group;
	}
	
	public int getNodeRadius() {
		
		return this.radius;
	}
	
	@XmlElement(name="survivalStatus")
	public int getSurvivorStatus() {
		
		return this.survivor;
	}
	
	@XmlElement(name="duration")
	public int getDuration() {
		
		return this.duration;
	}
}

package gr.cs.uoi.daintiness.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Represents the set of versions along with the including nodes and links
 * that are used to load projects.
 * @author KD
 * @since 2018-12-14
 * @version 1.0
 */

@XmlRootElement
public class Model {
	

	@XmlElementWrapper(name="versions")
	@XmlElement(name="version")
	private List<Version> versions = new ArrayList<Version>();
	
	public Model() {
		
		this.versions = new ArrayList<Version>();
		
	}
		
	public Model(List<Version> v) {
		
		this.versions = v;
		
	}
	
	
	public List<Version> getVersions(){
		
		return this.versions;
	}

}

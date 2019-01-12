package gr.cs.uoi.daintiness.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Represents the foreign key from "source" to "target".
 * @author KD
 * @since 2018-11-06
 * @version 1.0
 *
 */
@XmlRootElement(name="link")
public class Link {
	
	@XmlElement(name="source")
	private String source;
	@XmlElement(name="target")
	private String target;
	@XmlElement(name="value")
	private int value;
	
	public Link() {
		
		
	}
	
	public Link(String aSource, String aTarget) {
		
		this.source = aSource;
		this.target = aTarget;
		this.value = 1;
		
	}
	
	
	public String getSourceNode() {
		
		return this.source;
	}
	
	
	public String getTargetNode() {
		
		return this.target;
	}
	
	public String printLink() {
		
		return this.source+" -> "+this.target;
		
	}

}

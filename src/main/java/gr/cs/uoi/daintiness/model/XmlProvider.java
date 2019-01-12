package gr.cs.uoi.daintiness.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;


import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;


/**
 * Used to save/parse a project's model in xml format.
 * @author KD
 * @since 2018-12-13
 * @version 1.0
 */

public class XmlProvider {

	
	@SuppressWarnings("unused")
	private List<Node> nodes;
	@SuppressWarnings("unused")
	private List<Link> links;
	@SuppressWarnings("unused")
	private List<Version> versions;
	
	
	public XmlProvider() {
		
		nodes = new ArrayList<Node>();
		links = new ArrayList<Link>();
		versions = new ArrayList<Version>();
		
	}
	
	
	/**
	 * 
	 * @param model
	 * @param dir
	 */
	public void createXmlDoc(Object model,File dir) {
		
				
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(Model.class,
					Node.class,Link.class,Version.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(model, dir);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		

	}


	/**
	 * 
	 * @param modelFile
	 */
	@SuppressWarnings("unused")
	public void parseXmlDoc(File dir) {
		
		InputStream inStream;
		Model m = null;
		File modelFile = new File(dir+"/model.xml");
		
		List<String> nodesBucket = new ArrayList<String>();
		SetMultimap<String,String> linksBucket = HashMultimap.create();
		
		
		this.clear();
		
		try {
			inStream = new FileInputStream(modelFile.getAbsolutePath());
			JAXBContext context = JAXBContext.newInstance(Model.class,Node.class,
					Link.class,Version.class);
			Unmarshaller u = context.createUnmarshaller();
			m = (Model) u.unmarshal(inStream);
			
			versions = m.getVersions();
			

			//Set versions
			for (Version v:versions) {
				
				
				//Set nodes for DG
				for (Node nd:v.getVersionNodes()) {
					
					if(!nodesBucket.stream().anyMatch(str->str.trim().equals(nd.getNodeName()))) {
						
						Node n = new Node(nd.getNodeName(),0);
						n.setBirthVersion(nd.getBirthVersion());
						n.setSizeAtBirth(nd.getSizeAtBirth());
						n.setDuration(nd.getDuration());
						n.setChangesNum(nd.getChangesNum());
						n.setATU();
						//nd.setATU();
						n.setSurvivorStatus(nd.getSurvivorStatus());
						n.setRadius(n.getSizeAtBirth());
						nodes.add(n);
						nodesBucket.add(n.getNodeName());
					}
					
					
			
				
				//Set links for DG
				for (Link lk:v.getVersionLinks()) {
					
					//List<String> values = new ArrayList<String>();
					String source = lk.getSourceNode();
					String target = lk.getTargetNode();
					
					linksBucket.put(source, target);
					
						
				}
			}
				
	
				
			}
			
			@SuppressWarnings("rawtypes")
			Iterator keyIter = linksBucket.keySet().iterator();
			while (keyIter.hasNext()) {
				
				Object key = keyIter.next();
				@SuppressWarnings("rawtypes")
				Iterator valueIter = linksBucket.get(key.toString()).iterator();
				
				while(valueIter.hasNext()) {
					
					Object value = valueIter.next();
					Link l = new Link(key.toString(),value.toString());
					links.add(l);
					
				}
			}
			
			
		}catch(Exception e) {
			
			e.printStackTrace();
		}
		

	}
	
	
	public List<Node> getNodes(){
		
		return this.nodes;
	}
	
	public List<Link> getLinks(){
		
		return this.links;
	}
	
	public List<Version> getVersions(){
		
		return this.versions;
	}
	
	
	private void clear() {
		
		this.nodes.clear();
		this.links.clear();
		this.versions.clear();
		
	}
	

}

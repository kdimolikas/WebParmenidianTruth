package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import gr.cs.uoi.daintiness.core.ProjectProvider;
import gr.cs.uoi.daintiness.model.Link;

/**
 * Testing {@link gr.cs.uoi.daintiness.core.ProjectProvider} class.
 * @author KD
 * @since 2018-12-04
 *
 */
public class ProjectProviderTest {

	
	private static ProjectProvider projectProv;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		List<Link> links = new ArrayList<Link>();
		
		Link l1 = new Link("table1","table2");
		Link l2 = new Link("table3","table2");
		
		links.addAll(Arrays.asList(l1,l2));
		
		projectProv = ProjectProvider.getInstance();
		
		projectProv.setGroupedLinks(links);
		
	}

	@Test
	public void testGetInstance() {
		assertNotNull("Instance of ProjectProvider is not null",projectProv);
	}
	
	
	@Test
	public void testGetVersions() {
		assertNotNull("List of versions is not null",projectProv.getVersions());
	}
	
	
	@Test
	public void testGetNodes() {
		assertNotNull("List of nodes is not null",projectProv.getNodes());
	}
	
	
	@Test
	public void testGetLinks() {
		assertNotNull("List of links is not null",projectProv.getLinks());
	}
	
	
	
	@Test
	public void testConvertToJson() {
		
		assertEquals("List has 2 elements",2,projectProv.getGroupedLinks().size());
		System.out.println(projectProv.getLinksInJson());
		assertNotNull(projectProv.getLinksInJson());
		
	}


	@Test
	public void testGetLinksInJson() {
		
		assertFalse("List of links is not empty",projectProv.getLinksInJson().isEmpty());
		
		
	}

}

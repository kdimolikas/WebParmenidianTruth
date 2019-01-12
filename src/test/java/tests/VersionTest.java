package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import gr.cs.uoi.daintiness.model.Version;
import gr.cs.uoi.daintiness.model.Node;


/**
 * Testing {@link gr.cs.uoi.daintiness.model.Version} class.
 * @author KD
 * @since 2018-12-21
 * @version 1.0
 */
public class VersionTest {

	private static Version version;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		version = new Version("testVersion",1);
		
	}

	@Test
	public void testVersionStringInt() {
		assertNotNull("Version object is not null",version);
	}


	@Test
	public void testGetVersionNodes() {
		
		List<Node> testNodesList = new ArrayList<Node>();
		Node n1 = new Node("node1",1);
		Node n2 = new Node("node2",2);
		
		testNodesList.addAll(Arrays.asList(n1,n2));
		version.setNodes(testNodesList);
		
		assertEquals("Nodes of the version are 2",2,version.getVersionNodes().size());
	}


	@Test
	public void testGetVersionId() {
		assertEquals("Version id is equal to 1",1,version.getVersionId());
	}

}

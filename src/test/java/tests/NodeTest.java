package tests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import gr.cs.uoi.daintiness.model.Node;

/**
 * Testing {@link gr.cs.uoi.daintiness.model.Node} class.
 * @author KD
 * @since 2018-12-21
 * @version 1.0
 *
 */
public class NodeTest {

	private static Node testNode;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		testNode = new Node("node1",1);
		
	}

	@Test
	public void testNodeStringInt() {
		assertNotNull("Node object is not null",testNode);
	}

	@Test
	public void testGetNodeName() {
		assertEquals("Node name is node1","node1",testNode.getNodeName());
	}

	@Test
	public void testGetNodeGroup() {
		assertEquals("Node group is equal to 1",1,testNode.getNodeGroup());
	}

}

package tests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import gr.cs.uoi.daintiness.core.ParmenidianProvider;


/**
 * Testing {@link gr.cs.uoi.daintiness.core.ParmenidianProvider} class.
 * @author KD
 * @since 2018-12-21
 * @version 1.0
 */
public class ParmenidianProviderTest {

	
	private static ParmenidianProvider prmProv;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
		prmProv = ParmenidianProvider.getInstance();
		
		
	}

	@Test
	public void testGetInstance() {
		
		assertNotNull("Instance of ParmenidianProvider is not null",prmProv);
		
	}

	@Test
	public void testGetTables() {
		assertNotNull("List of tables is not null",prmProv.getTables());
	}

	@Test
	public void testGetForeignKeys() {
		assertNotNull("List of foreign keys is not null",prmProv.getForeignKeys());
	}

}

package gr.cs.uoi.daintiness.core;

import java.util.ArrayList;
import java.util.List;


import core.IParmenidianTruth;
import core.ParmenidianTruthManagerFactory;

import model.DBVersion;
import model.ForeignKey;
import model.Table;

/**
 * Provides data derived from ParmenidianTruth project.
 * @author KD
 * @since 2018-11-08
 * @version 1.0
 *
 */
public class ParmenidianProvider implements ParmenidianServer {
	
	
	private static final String TEST_FOLDER = "resources/Egee_test";
	private static final String OUTPUT_FOLDER = TEST_FOLDER.concat("/output");

	
	private static ParmenidianProvider instance = null;
	
	private IParmenidianTruth parmenidianManager;
	private ParmenidianTruthManagerFactory parmenidianFactory;
	private List<Table> tables;
	private List<ForeignKey> keys;
	
	//Singleton design pattern
	public static ParmenidianProvider getInstance() {
		
		if (instance == null)
			instance = new ParmenidianProvider();

		return instance;
	}
	
	
	private ParmenidianProvider() {
		
		tables = new ArrayList<Table>();
		keys = new ArrayList<ForeignKey>();
	}
	
	public void createProject (String sqlFolder) {
		
		clear();
		parmenidianFactory = new ParmenidianTruthManagerFactory();
		parmenidianManager = parmenidianFactory.getManager();
		parmenidianManager.loadProject(sqlFolder, OUTPUT_FOLDER);
		this.createTables();
		this.createForeignKeys();
		
	}
	
		
	public List<DBVersion> getVersions(){
		
		return parmenidianManager.getVersions();
	}
	
	
	
	public int getVersionsNum() {
		
		return parmenidianManager.getVersions().size();
		
	}
	
	public DBVersion getVersionWithId(int id) {
			
		return parmenidianManager.getVersionWithId(id); 
			
	}
	
	
	
	private void createTables() {
		
		for (Table t:parmenidianManager.getTables())
			tables.add(t);
			
	}
	
	private void createForeignKeys() {
		
		for (ForeignKey fk:parmenidianManager.getForeignKeys())
			keys.add(fk);
		
	}
	
	
	public List<Table> getTables(){
	
		return tables;
		
	}
	
	public List<ForeignKey> getForeignKeys(){
	
		return keys;
		
	}
	
	private  void clear() {
		
		tables.clear();
		keys.clear();
	}

}

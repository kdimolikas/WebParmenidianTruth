package gr.cs.uoi.daintiness.core;

import java.util.List;

import model.DBVersion;
import model.ForeignKey;
import model.Table;

/**
 * The interface of the parmenidian project.
 * @author KD
 * @since 2018-11-08
 * @version 1.0
 */
public interface ParmenidianServer {
	
	public void createProject (String sqlFolder);
	
	//public Map<String,Integer> createOverallStats();

	//public Map<String,Integer> createBirthVersionStats();
	
//	public Map<String,int[]> createGammaPatternStats();
//	
//	public Map<String,int[]> createGammaPatternData();
//	
//	public Map<String,int[]> createInvGammaPatternStats();
//	
//	public Map<String,int[]> createInvGammaPatternData();
//	
//	public Map<String,int[]> createCommetPatternStats();
//	
//	public Map<String,int[]> createCommetPatternData();
//	
//	public Map<String,int[]> createEmptyTrianglePatternStats();
//	
//	public Map<String,int[]> createEmptyTrianglePatternData();
	
	public List<DBVersion> getVersions();
	
	public int getVersionsNum();

	public List<Table> getTables();
	
	public DBVersion getVersionWithId(int id);

	public List<ForeignKey> getForeignKeys();
	
	//public int getTableUpdateClass(int updNum,double atu);
	
	//public String getBirthVersionLabel(int dur);
	
	//public String getActivityLabel(int chng,double atu);
	
	//public String getSurvivalLabel(int status);
	
}
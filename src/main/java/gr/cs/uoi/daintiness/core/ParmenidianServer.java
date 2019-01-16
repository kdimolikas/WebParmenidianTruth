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
	
	public List<DBVersion> getVersions();
	
	public int getVersionsNum();

	public List<Table> getTables();
	
	public DBVersion getVersionWithId(int id);

	public List<ForeignKey> getForeignKeys();
	
	
}
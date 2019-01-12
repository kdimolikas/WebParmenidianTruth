package gr.cs.uoi.daintiness.core;

import java.util.List;
import java.util.Map;

import gr.cs.uoi.daintiness.enums.ActivityStatus;
import gr.cs.uoi.daintiness.enums.BirthVersionLabel;
import gr.cs.uoi.daintiness.enums.DurationLabel;
import gr.cs.uoi.daintiness.enums.Labels;
import gr.cs.uoi.daintiness.enums.SurvivalStatus;
import gr.cs.uoi.daintiness.model.Node;


/**
 * 
 * @author KD
 * @since 2018-12-15
 * @version 1.0
 *
 */
public interface StatisticsServer {
	
	
	public Map<String,Integer> createOverallStats(Map<Labels,Integer> st );
	public Map<String,Integer> createBirthVersionStats(Map<BirthVersionLabel,Integer> birthStats);
	public Map<String,Integer> createActivityStats(Map<ActivityStatus,Integer> act);
	public String getActivityLabel(int chng,double atu);
	public String getSurvivalLabel(int status);
	public int getTableUpdateClass(int updNum,double atu);
	public String getBirthVersionLabel(int birthVersion,int versionsNum);
	public Map<String,int[]> createGammaPatternStats(List<Node> nodes,int versionsNum);
	public Map<String,int[]> createGammaPatternData(List<Node> nodes);
	public Map<String,int[]> createInvGammaPatternStats(List<Node> nodes,int vNum);
	public Map<String,int[]> createInvGammaPatternData(List<Node> nodes);
	public Map<String,int[]> createCommetPatternStats(List<Node> nodes);
	public Map<String,int[]> createCommetPatternData(List<Node> nodes);
	public Map<String,int[]> createEmptyTrianglePatternStats(List<Node> nodes,int versionsNum);
	public Map<String,int[]> createEmptyTrianglePatternData(List<Node> nodes);
	public Map<String, Integer> createSurvivalStats(Map<SurvivalStatus, Integer> surv);
	public int getTableNormCategory(int dur,int versionsNum );
	public Map<String, Integer> createDurationStats(Map<DurationLabel, Integer> dur);
	

}

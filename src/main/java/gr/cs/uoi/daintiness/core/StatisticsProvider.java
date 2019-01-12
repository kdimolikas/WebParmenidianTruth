package gr.cs.uoi.daintiness.core;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import gr.cs.uoi.daintiness.enums.ActivityStatus;
import gr.cs.uoi.daintiness.enums.BirthVersionLabel;
import gr.cs.uoi.daintiness.enums.DurationLabel;
import gr.cs.uoi.daintiness.enums.Labels;
import gr.cs.uoi.daintiness.enums.SurvivalStatus;
import gr.cs.uoi.daintiness.model.Node;

public class StatisticsProvider implements StatisticsServer {

	
	private static final String SHORT_LIVED = "1.Short Lived(<0.33)";
	private static final String MEDIUM_LIVED = "2.Medium Lived(>=0.33 and <=0.77)";
	private static final String LONG_LIVED = "3.Long Lived(>0.77)";
	private static final String SMALL_SIZE = "1.Small(<5)";
	private static final String MEDIUM_SIZE = "2.Medium(>=5 and <=10)";
	private static final String WIDE_SIZE = "3.Wide(>10)";
	private static final String EARLY_BORN = "Early Born(<33%)";
	private static final String MEDIUM_BORN = "Medium Born(>=33% and <=77%)";
	private static final String LATE_BORN = "Late Born(>77%)";
	private static final String SURVIVORS = "Survivors";
	private static final String NON_SURVIVORS = "Non-survivors";
	
	//protected BirthVersionLabel birthLabel;
	
	
	@Override
	public Map<String, Integer> createOverallStats(Map<Labels,Integer> st ) {

		Map<String,Integer> stats = new TreeMap<String,Integer>();
		
		stats.put("Number of tables", st.get(Labels.TABLES));
		stats.put("Number of foreign keys", st.get(Labels.KEYS) );
		stats.put("Number of versions", st.get(Labels.VERSIONS));
		
		return stats;
		
	}

	@Override
	public Map<String, Integer> createBirthVersionStats(Map<BirthVersionLabel,Integer> birthStats) {

		Map<String,Integer> stats = new TreeMap<String,Integer>();
		
		
		stats.put("0%-33%", 0);
		stats.put("33%-77%", 0);
		stats.put(">77%", 0);
		

		stats.put("0%-33%",birthStats.get(BirthVersionLabel.EARLY_BORN));
		stats.put("33%-77%",birthStats.get(BirthVersionLabel.MEDIUM_BORN));
		stats.put(">77%",birthStats.get(BirthVersionLabel.LATE_BORN));

		
		return stats;
		
	}

	
	/**
	 * 
	 * @param dur - table's duration in number of versions.
	 * @return a label corresponding to table's birth version category
	 */
	public String getBirthVersionLabel(int birthVersion,int versionsNum) {
		
		if (this.getTableNormCategory(birthVersion,versionsNum)==1)
			return BirthVersionLabel.EARLY_BORN.toString();
		else if (this.getTableNormCategory(birthVersion,versionsNum)==2)
			return BirthVersionLabel.MEDIUM_BORN.toString();
		else
			return BirthVersionLabel.LATE_BORN.toString();
		
	}
	
	
	/**
	 * Used to compute the Birth Version class and the Duration class of a table.
	 * @param dur-table's duration (in #versions).
	 * @return an int that signifies the normalized duration category.
	 * @author KD
	 * @since 2018-11-27
	 */
	public int getTableNormCategory(int dur,int versionsNum ) {
		
		double normDuration = dur/(double)versionsNum;
		
		if (Double.compare(normDuration,0.33)<0)
			return 1;//Early Born or Short Lived
		else if (Double.compare(normDuration,0.33)>=0 && Double.compare(normDuration,0.77)<=0)
			return 2;//Medium Born or Medium Lived
		else 
			return 3;//Late Born or Long Lived

		
	}
	
	
	/**
	 * 
	 * @param chng - number of changes.
	 * @param atu - Average Transitional Update = sum(updates)/duration.
	 * @return a label corresponding to table's activity status.
	 */
	public String getActivityLabel(int chng,double atu) {
		
		if (this.getTableUpdateClass(chng, atu)==ActivityStatus.RIGID.getValue())
			return ActivityStatus.RIGID.toString();
		else if (this.getTableUpdateClass(chng, atu)==ActivityStatus.QUIET.getValue())
			return ActivityStatus.QUIET.toString();
		else
			return ActivityStatus.ACTIVE.toString();
		
	}
	
	
	/**
	 * Defines the update activity class of a table.
	 * @return the activity class of the table.
	 * @author KD
	 * @since 2018-11-30
	 */
	public int getTableUpdateClass(int updNum,double atu) {
		
			
		if (updNum>5 && Double.compare(atu, 0.1)>0)
			return ActivityStatus.ACTIVE.getValue();
		else if (updNum > 0 )
			return ActivityStatus.QUIET.getValue();
		else 
			return ActivityStatus.RIGID.getValue();
		
	}
	
	
	/**
	 * 
	 * @param status - 0 for non-survivors, 1 for survivors
	 * @return a label corresponding to table's susrvival status.
	 */
	public String getSurvivalLabel(int status) {
		
		if (status==SurvivalStatus.NON_SURVIVOR.getValue())
			return SurvivalStatus.NON_SURVIVOR.toString();
		else
			return SurvivalStatus.SURVIVOR.toString();
		
	}
	
	
	
	/**
	 * @return a map with tables' sizes (in #attributes) at birth version and distribution of tables over 
	 * duration categories as values.
	 * @author KD
	 * @since 2018-11-27
	 */
	public Map<String,int[]> createGammaPatternStats(List<Node> nodes,int versionsNum){
		
		Map<String,int[]> gamma = new TreeMap<String,int[]>();
		
		gamma.put(SMALL_SIZE,new int[3]);
		gamma.put(MEDIUM_SIZE,new int[3]);
		gamma.put(WIDE_SIZE,new int[3]);
		
		for (Node n:nodes) {
			
			int size = n.getSizeAtBirth();
			int dur = n.getDuration();
			
			if (size<5) {
				if (this.getTableNormCategory(dur,versionsNum)==1)
					gamma.get(SMALL_SIZE)[0]++;
				else if (this.getTableNormCategory(dur,versionsNum)==2)
					gamma.get(SMALL_SIZE)[1]++;
				else if (this.getTableNormCategory(dur,versionsNum)==3)
					gamma.get(SMALL_SIZE)[2]++;
				
			}else if (size>=5 && size<=10) {
				if (this.getTableNormCategory(dur,versionsNum)==1)
					gamma.get(MEDIUM_SIZE)[0]++;
				else if (this.getTableNormCategory(dur,versionsNum)==2)
					gamma.get(MEDIUM_SIZE)[1]++;
				else if (this.getTableNormCategory(dur,versionsNum)==3)
					gamma.get(MEDIUM_SIZE)[2]++;
			
			}else if (size>10) {
				if (this.getTableNormCategory(dur,versionsNum)==1)
					gamma.get(WIDE_SIZE)[0]++;
				else if (this.getTableNormCategory(dur,versionsNum)==2)
					gamma.get(WIDE_SIZE)[1]++;
				else if (this.getTableNormCategory(dur,versionsNum)==3)
					gamma.get(WIDE_SIZE)[2]++;
			
			}
		}	
		
		return gamma;
	}
	
	
	
	
	/**
	 * Creates the data for the gamma pattern (Tables' size vs Duration)
	 * @return a map with tables' names as keys and size-duration as values
	 * @author KD
	 * @since 2018-11-28
	 */
	public Map<String,int[]> createGammaPatternData(List<Node> nodes){
		
		Map<String,int[]> gamma = new TreeMap<String,int[]>();
		
		for (Node n:nodes) {
			
			int size = n.getSizeAtBirth();
			int dur = n.getDuration();
			int[] values = new int[4];
			
			values[0] = size;
			values[1] = dur;
			values[2] = n.getSizeAtBirth();
			values[3] = this.getTableUpdateClass(n.getChangesNum(),n.getATU());
			
			gamma.put(n.getNodeName(),values);
					
		}
		
		return gamma;
		
	}
	
	
	/**
	 * 
	 * @return a map with durations as keys and tables' distribution over activity classes as values.
	 * @author KD
	 * @since 2018-11-27
	 */
	public Map<String,int[]> createInvGammaPatternStats(List<Node> nodes,int vNum){
		
		Map<String,int[]> invGamma = new TreeMap<String,int[]>();
		
		invGamma.put(SHORT_LIVED, new int[3]);
		invGamma.put(MEDIUM_LIVED, new int[3]);
		invGamma.put(LONG_LIVED, new int[3]);
		
		for (Node n:nodes) {
			
			if (this.getTableNormCategory(n.getDuration(),vNum)==1) {
				if (getTableUpdateClass(n.getChangesNum(), n.getATU())==ActivityStatus.RIGID.getValue())
					invGamma.get(SHORT_LIVED)[0]++;
				else if (getTableUpdateClass(n.getChangesNum(), n.getATU())==ActivityStatus.QUIET.getValue())
					invGamma.get(SHORT_LIVED)[1]++;
				else if (getTableUpdateClass(n.getChangesNum(),n.getATU())==ActivityStatus.ACTIVE.getValue())
					invGamma.get(SHORT_LIVED)[2]++;
			
			}else if (this.getTableNormCategory(n.getDuration(),vNum)==2) {
				if (getTableUpdateClass(n.getChangesNum(), n.getATU())==ActivityStatus.RIGID.getValue())
					invGamma.get(MEDIUM_LIVED)[0]++;
				else if (getTableUpdateClass(n.getChangesNum(), n.getATU())==ActivityStatus.QUIET.getValue())
					invGamma.get(MEDIUM_LIVED)[1]++;
				else if (getTableUpdateClass(n.getChangesNum(), n.getATU())==ActivityStatus.ACTIVE.getValue())
					invGamma.get(MEDIUM_LIVED)[2]++;
			
			}else if (this.getTableNormCategory(n.getDuration(),vNum)==3) {
				if (getTableUpdateClass(n.getChangesNum(), n.getATU())==ActivityStatus.RIGID.getValue())
					invGamma.get(LONG_LIVED)[0]++;
				else if (getTableUpdateClass(n.getChangesNum(), n.getATU())==ActivityStatus.QUIET.getValue())
					invGamma.get(LONG_LIVED)[1]++;
				else if (getTableUpdateClass(n.getChangesNum(), n.getATU())==ActivityStatus.ACTIVE.getValue())
					invGamma.get(LONG_LIVED)[2]++;
			}
				
		}
		
		return invGamma;
	}
	
	
	/**
	 * Creates the data for the inverse gamma pattern (Duration vs sum(updates)
	 * @return a map with tables' names as keys and duration-sum(updates) as values
	 * @author KD
	 * @since 2018-11-30
	 */
	public Map<String,int[]> createInvGammaPatternData(List<Node> nodes){
		
		Map<String,int[]> invGamma = new TreeMap<String,int[]>();
		
		for (Node n:nodes) {
			
			int upd = n.getChangesNum();
			int dur = n.getDuration();
			int[] values = new int[4];
			
			values[0] = dur;
			values[1] = upd;
			values[2] = n.getSizeAtBirth();
			values[3] = this.getTableUpdateClass(n.getChangesNum(), n.getATU());
			
			invGamma.put(n.getNodeName(),values);
					
		}
		
		return invGamma;
		
	}
	
	
	/**
	 * 
	 * @return a map with tables' sizes (in #attributes) at birth version as keys and 
	 * tables' distribution over activity classes as values.
	 * @author KD
	 * @since 2018-11-27
	 */
	public Map<String,int[]> createCommetPatternStats(List<Node> nodes){
		
		Map<String,int[]> commet = new TreeMap<String,int[]>();
		
		commet.put(SMALL_SIZE,new int[3]);
		commet.put(MEDIUM_SIZE,new int[3]);
		commet.put(WIDE_SIZE,new int[3]);
		
		for (Node n:nodes) {
			
			int size = n.getSizeAtBirth();
			int changes = n.getChangesNum();
			double atu = n.getATU();
			
			if (size<5) {
				if (getTableUpdateClass(changes, atu)==ActivityStatus.RIGID.getValue())
					commet.get(SMALL_SIZE)[0]++;
				else if (getTableUpdateClass(changes, atu)==ActivityStatus.QUIET.getValue())
					commet.get(SMALL_SIZE)[1]++;
				else if (getTableUpdateClass(changes, atu)==ActivityStatus.ACTIVE.getValue())
					commet.get(SMALL_SIZE)[2]++;
			
			}else if (size>=5 && size<=10) {
				if (getTableUpdateClass(changes, atu)==ActivityStatus.RIGID.getValue())
					commet.get(MEDIUM_SIZE)[0]++;
				else if (getTableUpdateClass(changes, atu)==ActivityStatus.QUIET.getValue())
					commet.get(MEDIUM_SIZE)[1]++;
				else if (getTableUpdateClass(changes, atu)==ActivityStatus.ACTIVE.getValue())
					commet.get(MEDIUM_SIZE)[2]++;
			
			}else if (size>10) {
				if (getTableUpdateClass(changes, atu)==ActivityStatus.RIGID.getValue())
					commet.get(WIDE_SIZE)[0]++;
				else if (getTableUpdateClass(changes, atu)==ActivityStatus.QUIET.getValue())
					commet.get(WIDE_SIZE)[1]++;
				else if (getTableUpdateClass(changes, atu)==ActivityStatus.ACTIVE.getValue())
					commet.get(WIDE_SIZE)[2]++;
			}
		
		
		}
		
		return commet;
	}
	
	
	/**
	 * Creates the data for the Commet pattern (Size vs sum(updates)
	 * @return a map with tables' names as keys and size-sum(updates) as values
	 * @author KD
	 * @since 2018-11-30
	 */
	public Map<String,int[]> createCommetPatternData(List<Node> nodes){
		
		Map<String,int[]> commet = new TreeMap<String,int[]>();
		
		for (Node n:nodes) {
			
			int size = n.getSizeAtBirth();
			int upd = n.getChangesNum();
			int[] values = new int[4];
			
			values[0] = size;
			values[1] = upd;
			values[2] = n.getSizeAtBirth();//radius attr of Bubble obj
			values[3] = getTableUpdateClass(n.getChangesNum(),n.getATU());//color attr of Bubble obj
			
			commet.put(n.getNodeName(),values);
					
		}
		
		return commet;
		
	}
	
	
	/**
	 * 
	 * @return a map with birth versions as keys and tables' distribution over duration classes as values.
	 * @author KD
	 * @since 2018-11-27
	 */
	public Map<String,int[]> createEmptyTrianglePatternStats(List<Node> nodes,int versionsNum){
		
		Map<String,int[]> trng = new TreeMap<String,int[]>();
		
		trng.put(EARLY_BORN,new int[3]);
		trng.put(MEDIUM_BORN,new int[3]);
		trng.put(LATE_BORN,new int[3]);
		
		for (Node n:nodes) {
			
			int birthVersionCategory = this.getTableNormCategory(n.getBirthVersion(), versionsNum);
			int dur = n.getDuration();
			
			if (birthVersionCategory==1) {
				if (this.getTableNormCategory(dur,versionsNum)==1)
					trng.get(EARLY_BORN)[0]++;
				else if (this.getTableNormCategory(dur,versionsNum)==2)
					trng.get(EARLY_BORN)[1]++;
				else if (this.getTableNormCategory(dur,versionsNum)==3)
					trng.get(EARLY_BORN)[2]++;
				
			}else if (birthVersionCategory==2) {
				if (this.getTableNormCategory(dur,versionsNum)==1)
					trng.get(MEDIUM_BORN)[0]++;
				else if (this.getTableNormCategory(dur,versionsNum)==2)
					trng.get(MEDIUM_BORN)[1]++;
				else if (this.getTableNormCategory(dur,versionsNum)==3)
					trng.get(MEDIUM_BORN)[2]++;
			
			}else if (birthVersionCategory==3) {
				if (this.getTableNormCategory(dur,versionsNum)==1)
					trng.get(LATE_BORN)[0]++;
				else if (this.getTableNormCategory(dur,versionsNum)==2)
					trng.get(LATE_BORN)[1]++;
				else if (this.getTableNormCategory(dur,versionsNum)==3)
					trng.get(LATE_BORN)[2]++;
			}
			
		}
		
		
		return trng;
		
	}
	
	
	/**
	 * Creates the data for the Empty Triangle pattern (Birth Version vs Duration) 
	 * @return a map with tables' names as keys and birth version-duration as values
	 * @author KD
	 * @since 2018-11-28
	 */
	public Map<String,int[]> createEmptyTrianglePatternData(List<Node> nodes){
		
		Map<String,int[]> triangle = new TreeMap<String,int[]>();
		
		for (Node n:nodes) {
			
			int birthVersion = n.getBirthVersion();
			int dur = n.getDuration();
			int[] values = new int[4];
			
			values[0] = birthVersion;
			values[1] = dur;
			values[2] = n.getSizeAtBirth();//radius attr of Bubble obj
			values[3] = getTableUpdateClass(n.getChangesNum(),n.getATU());//color attr of Bubble obj
			
			triangle.put(n.getNodeName(),values);
					
		}
		
		return triangle;
		
	}

	@Override
	public Map<String,Integer> createDurationStats(Map<DurationLabel,Integer> dur){
		
		Map<String,Integer> stats = new TreeMap<String,Integer>();
		
		stats.put(SHORT_LIVED, 0);
		stats.put(MEDIUM_LIVED, 0);
		stats.put(LONG_LIVED, 0);
		
		stats.put(SHORT_LIVED, dur.get(DurationLabel.SHORT_LIVED));
		stats.put(MEDIUM_LIVED, dur.get(DurationLabel.MEDIUM_LIVED));
		stats.put(LONG_LIVED, dur.get(DurationLabel.LONG_LIVED));
		
		return stats;
		
	}
	
	
	@Override
	public Map<String, Integer> createSurvivalStats(Map<SurvivalStatus,Integer> surv) {
		
		Map<String,Integer> stats = new TreeMap<String,Integer>();
		
		stats.put(SURVIVORS, 0);
		stats.put(NON_SURVIVORS, 0);
		
		stats.put(SURVIVORS, surv.get(SurvivalStatus.SURVIVOR));
		stats.put(NON_SURVIVORS, surv.get(SurvivalStatus.NON_SURVIVOR));
		
		return stats;
	}
	
	

	@Override
	public Map<String, Integer> createActivityStats(Map<ActivityStatus,Integer> act) {
		
		Map<String,Integer> stats = new TreeMap<String,Integer>();
		
		stats.put(ActivityStatus.RIGID.toString(), 0);
		stats.put(ActivityStatus.QUIET.toString(), 0);
		stats.put(ActivityStatus.ACTIVE.toString(), 0);
		
		stats.put(ActivityStatus.RIGID.toString(), act.get(ActivityStatus.RIGID));
		stats.put(ActivityStatus.QUIET.toString(), act.get(ActivityStatus.QUIET));
		stats.put(ActivityStatus.ACTIVE.toString(), act.get(ActivityStatus.ACTIVE));
		
		
		return stats;
	}

}

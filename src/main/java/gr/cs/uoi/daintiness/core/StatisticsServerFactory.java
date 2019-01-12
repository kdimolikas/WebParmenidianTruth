package gr.cs.uoi.daintiness.core;


/**
 * 
 * @author KD
 * @since 2018-12-15
 *
 */
public class StatisticsServerFactory {
	
	
	public StatisticsServer createStatisticsServer() {
		
		return new StatisticsProvider();
		
	}

}

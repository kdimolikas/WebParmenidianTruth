package gr.cs.uoi.daintiness.enums;


/**
 * Defines three table categories based on tables' normalized duration.
 * @author KD
 * @since 2018-12-21
 */
public enum DurationLabel {
	
		
	SHORT_LIVED(0),MEDIUM_LIVED(1),LONG_LIVED(2);
	
	private int value;
	
	private DurationLabel(int val) {
		
		this.value = val;
		
	}
	
	public int getValue() {
		
		return value;
	}

}
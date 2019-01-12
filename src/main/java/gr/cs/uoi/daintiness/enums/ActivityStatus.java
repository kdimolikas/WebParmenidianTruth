package gr.cs.uoi.daintiness.enums;


/**
 * Defines tables' activity status.
 * @author KD
 * @since 2018-12-09
 */
public enum ActivityStatus {
	
	RIGID(0),QUIET(1),ACTIVE(2);
	
	private int value;
	
	private ActivityStatus(int val) {
		
		this.value = val;
		
	}
	
	public int getValue() {
		
		return value;
	}
	
	

}

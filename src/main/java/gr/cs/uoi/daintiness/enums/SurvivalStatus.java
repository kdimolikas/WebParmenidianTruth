/**
 * 
 */
package gr.cs.uoi.daintiness.enums;

/**
 * Defines survival status of PT tables.
 * @author KD
 * @since 2018-12-10
 */
public enum SurvivalStatus {
	
	NON_SURVIVOR(0),SURVIVOR(1);
	
	private int value;
	
	private SurvivalStatus(int val) {
		
		this.value = val;
		
	}
	
	public int getValue() {
		
		return value;
	}
	

}

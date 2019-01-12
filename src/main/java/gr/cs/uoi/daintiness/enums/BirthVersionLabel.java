/**
 * 
 */
package gr.cs.uoi.daintiness.enums;

/**
 * Defines three categories based on tables' birth versions.
 * @author KD
 * @since 2018-12-10
 * @version 1.0
 */
public enum BirthVersionLabel {
	
	EARLY_BORN(0),MEDIUM_BORN(1),LATE_BORN(2);
	
	private int value;
	
	private BirthVersionLabel(int val) {
		
		this.value = val;
		
	}
	
	public int getValue() {
		
		return value;
	}
	

}

package gr.cs.uoi.daintiness.core;


/**
 * Simulates the visualized nodes of the Diachronic Graph.
 * @author KD
 * @since 2018-11
 */
public class Bubble {
	
	@SuppressWarnings("unused")
	private String name;
	@SuppressWarnings("unused")
	private int xValue;
	@SuppressWarnings("unused")
	private int yValue;
	@SuppressWarnings("unused")
	private double rad;
	@SuppressWarnings("unused")
	private double col;

	
	public Bubble(String n, int x, int y, double r, double c) {
		
		this.name = n;
		this.xValue = x;
		this.yValue = y;
		this.rad = r;
		this.col = c;
		
	}

	
}

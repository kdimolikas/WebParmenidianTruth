package gr.cs.uoi.daintiness.core;

public class ParmenidianServerFactory {
	
	
	public ParmenidianServer createParmenidianServer() {
		
		return ParmenidianProvider.getInstance();
		
	}

}
package gr.cs.uoi.daintiness;

import org.eclipse.jetty.server.Server;

/**
 * Used as entry point for the web app.
 * @author KD
 * @since 2018-11-02
 */
public class Main{
	
		
	public static void main(String[] args) {
		
		
		Server jetty = new Server(8080);
		
		try {
			jetty.start();
			//jetty.dumpStdErr();
			jetty.join();
		}catch(Exception e) {
			
			e.printStackTrace();
		}
		

	}
	
	

}
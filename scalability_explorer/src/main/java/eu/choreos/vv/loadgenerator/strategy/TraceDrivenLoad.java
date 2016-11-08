package eu.choreos.vv.loadgenerator.strategy;

import java.io.BufferedReader;

public class TraceDrivenLoad extends LoadGenerationStrategy {
	
	private Long timeBefore, sleepInterval;
	private BufferedReader in;
	
	public TraceDrivenLoad(BufferedReader source) {
		this.in = source;
	}
		
	@Override
	public void beforeRequest() throws Exception {
		timeBefore = System.nanoTime();
		try {
			sleepInterval = Long.parseLong(in.readLine());
		} catch (Exception e) {
			sleepInterval = 0l;
		}
	}
	
	@Override
	public void afterRequest() throws Exception {
		Long now = System.nanoTime();
		long sleepTime = sleepInterval - now + timeBefore; 
		if (sleepTime > 0)
			sleep(sleepTime);
		
		
	}

}

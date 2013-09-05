package eu.choreos.vv.loadgenerator.strategy;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import eu.choreos.vv.loadgenerator.BasicLoadGenerator;

public class DegeneratedLoad extends LoadGenerationStrategy {


	@Override
	public void afterRequest() throws Exception {
		super.afterRequest();
		sleep(delay - end + start);
	}

	
	
	

}
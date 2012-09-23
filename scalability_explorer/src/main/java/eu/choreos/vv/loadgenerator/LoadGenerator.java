package eu.choreos.vv.loadgenerator;

import java.util.List;

import eu.choreos.vv.loadgenerator.executable.Executable;

public interface LoadGenerator {
	
	public List<Number> execute(int times, int callsPerMin, Executable exec) throws Exception;

}

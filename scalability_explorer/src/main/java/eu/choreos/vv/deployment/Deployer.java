package eu.choreos.vv.deployment;

import java.util.List;



public interface Deployer {
	
	public void enact() throws Exception;
	public List<String> getServiceUris(String serviceName);
	public void scale(int index) throws Exception;

}

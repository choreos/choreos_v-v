package eu.choreos.vv.deployment;

import java.util.List;
import java.util.Map;



public interface Deployer {
	
	public void deploy() throws Exception;
	public List<String> getServiceUris(String serviceName);
	public void scale(Map<String, Object> params) throws Exception;

}

package eu.choreos.vv.deployment;



public interface Deployer {
	
	public void enact() throws Exception;
	public String getServiceUri(String serviceName);
	public void scale(int index) throws Exception;

}

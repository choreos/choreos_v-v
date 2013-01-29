package eu.choreos.vv.deployment;



public interface Deployer {
	
	public void enactChoreography() throws Exception;
	public String getServiceUri(String serviceName);
	public void scale(int index) throws Exception;

}

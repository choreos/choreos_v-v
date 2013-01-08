package eu.choreos.vv.enactment;



public interface Enacter {
	
	public void enactChoreography() throws Exception;
	public String getServiceUri(String serviceName);
	public void scale(int index) throws Exception;

}

package eu.choreos.vv.abstractor;

/**
 *  * This class represents the role object and belongs to the Abstraction Choreography feature
 * 
 * @author Felipe Besson
 *
 */
public class Role {

	private String name;
	private String contractUri;

	public Role(){}
	
	public Role(String name, String wsdl) {
		this.setName(name);
		this.setContractUri(wsdl);
        }

	public String getName() {
	        return name;
        }

	public void setName(String name) {
	        this.name = name;
        }
	
	public String getContractUri() {
		return contractUri;
	}
	
	public void setContractUri(String contractUri) {
		this.contractUri = contractUri;
	}

}

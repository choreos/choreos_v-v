package eu.choreos.vv.abstractor;

/**
 *  * This class represents the role object and belongs to the Abstraction Choreography feature
 * 
 * @author Felipe Besson
 *
 */
public class Role {

	private String name;
	private String wsdl;
	
	public Role(String name, String wsdl) {
		this.setName(name);
		this.setWsdl(wsdl);
        }

	public String getName() {
	        return name;
        }

	public void setName(String name) {
	        this.name = name;
        }

	public String getWsdl() {
	        return wsdl;
        }

	public void setWsdl(String wsdl) {
	        this.wsdl = wsdl;
        }

	@Override
        public int hashCode() {
	        final int prime = 31;
	        int result = 1;
	        result = prime * result + ((name == null) ? 0 : name.hashCode());
	        result = prime * result + ((wsdl == null) ? 0 : wsdl.hashCode());
	        return result;
        }

	@Override
        public boolean equals(Object obj) {
	        if (this == obj)
		        return true;
	        if (obj == null)
		        return false;
	        if (getClass() != obj.getClass())
		        return false;
	        Role other = (Role) obj;
	        if (name == null) {
		        if (other.name != null)
			        return false;
	        } else if (!name.equals(other.name))
		        return false;
	        if (wsdl == null) {
		        if (other.wsdl != null)
			        return false;
	        } else if (!wsdl.equals(other.wsdl))
		        return false;
	        return true;
        }
	

}

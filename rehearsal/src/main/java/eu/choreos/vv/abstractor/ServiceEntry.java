package eu.choreos.vv.abstractor;

import java.util.List;

/**
 * This class represents the service elements of the Choreography Descriptor (yaml file)
 * 
 * @author besson
 *
 */
public class ServiceEntry {
	
	private String uri;
	private Role role;
	private List<ServiceEntry> participants;
	
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public List<ServiceEntry> getParticipants() {
		return participants;
	}
	public void setParticipants(List<ServiceEntry> participants) {
		this.participants = participants;
	}

}

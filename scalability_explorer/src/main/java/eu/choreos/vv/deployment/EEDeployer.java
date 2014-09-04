package eu.choreos.vv.deployment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ow2.choreos.chors.ChoreographyNotFoundException;
import org.ow2.choreos.chors.DeploymentException;
import org.ow2.choreos.chors.client.EEClient;
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.chors.datamodel.ChoreographySpec;
import org.ow2.choreos.services.datamodel.Service;

public abstract class EEDeployer implements Deployer {

	private EEClient eeClient;
	private Map<String, List<String>> deployedServices;
	private String deploymentID;

	protected abstract ChoreographySpec enactmentSpec();

	protected abstract ChoreographySpec scaleSpec(Map<String, Object> params);

	public EEDeployer(String host) {
		eeClient = new EEClient(host);
		deployedServices = new HashMap<String, List<String>>();
	}

	@Override
	public void deploy() throws Exception {
		ChoreographySpec enactSpec = enactmentSpec();
		deploymentID = enact(enactSpec);
	}

	@Override
	public List<String> getServiceUris(String serviceName) {
		return deployedServices.get(serviceName);
	}

	@Override
	public void scale(Map<String, Object> params) throws Exception {
		ChoreographySpec scaleSpec = scaleSpec(params);
		update(scaleSpec);
	}

	private String enact(ChoreographySpec spec)
			throws ChoreographyNotFoundException, DeploymentException {
		final String chorId = eeClient.createChoreography(spec);
		final Choreography chor = eeClient.deployChoreography(chorId);
		storeServices(chor);
		return chorId;
	}
	
	private void update(ChoreographySpec spec)
			throws ChoreographyNotFoundException, DeploymentException {
		eeClient.updateChoreography(deploymentID, spec);
		final Choreography chor = eeClient.getChoreography(deploymentID);
		storeServices(chor);
	}

	private void storeServices(final Choreography chor) {
		deployedServices = new HashMap<String, List<String>>();
		for (Service service : chor.getServices()) {
			deployedServices
					.put(service.getSpec().getName(), service.getUris());
		}
	}

}

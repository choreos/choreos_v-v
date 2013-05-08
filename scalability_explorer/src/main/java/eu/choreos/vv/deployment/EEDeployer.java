package eu.choreos.vv.deployment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ow2.choreos.chors.ChoreographyNotFoundException;
import org.ow2.choreos.chors.EnactmentException;
import org.ow2.choreos.chors.client.ChorDeployerClient;
import org.ow2.choreos.chors.datamodel.ChorSpec;
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.deployment.services.datamodel.Service;

public abstract class EEDeployer implements Deployer {

	private ChorDeployerClient eeClient;
	private Map<String, List<String>> deployedServices;

	protected abstract ChorSpec enactmentSpec();

	protected abstract ChorSpec scaleSpec(int idx);

	public EEDeployer(String host) {
		eeClient = new ChorDeployerClient(host);
		deployedServices = new HashMap<String, List<String>>();
	}

	@Override
	public void enact() throws Exception {
		ChorSpec enactSpec = enactmentSpec();
		enact(enactSpec);
	}

	@Override
	public List<String> getServiceUris(String serviceName) {
		return deployedServices.get(serviceName);
	}

	@Override
	public void scale(int index) throws Exception {
		ChorSpec scaleSpec = scaleSpec(index);
		enact(scaleSpec);
	}

	private void enact(ChorSpec spec) throws EnactmentException,
			ChoreographyNotFoundException {
		final String chorId = eeClient.createChoreography(spec);
		final Choreography chor = eeClient.enact(chorId);
		storeServices(chor);
	}

	private void storeServices(final Choreography chor) {
		for (Service service : chor.getDeployedServices()) {
			deployedServices.put(service.getName(), service.getUris());
		}
	}

}

package eu.choreos.vv.deployment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ow2.choreos.chors.ChoreographyNotFoundException;
import org.ow2.choreos.chors.EnactmentException;
import org.ow2.choreos.chors.client.ChorDeployerClient;
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.chors.datamodel.ChoreographySpec;
import org.ow2.choreos.services.datamodel.Service;

public abstract class EEDeployer implements Deployer {

	private ChorDeployerClient eeClient;
	private Map<String, List<String>> deployedServices;

	protected abstract ChoreographySpec enactmentSpec();

	protected abstract ChoreographySpec scaleSpec(int idx);

	public EEDeployer(String host) {
		eeClient = new ChorDeployerClient(host);
		deployedServices = new HashMap<String, List<String>>();
	}

	@Override
	public void enact() throws Exception {
		ChoreographySpec enactSpec = enactmentSpec();
		enact(enactSpec);
	}

	@Override
	public List<String> getServiceUris(String serviceName) {
		return deployedServices.get(serviceName);
	}

	@Override
	public void scale(int index) throws Exception {
		ChoreographySpec scaleSpec = scaleSpec(index);
		enact(scaleSpec);
	}

	private void enact(ChoreographySpec spec) throws EnactmentException,
			ChoreographyNotFoundException {
		final String chorId = eeClient.createChoreography(spec);
		final Choreography chor = eeClient.enactChoreography(chorId);
		storeServices(chor);
	}

	private void storeServices(final Choreography chor) {
		deployedServices = new HashMap<String, List<String>>(); 
		for (Service service : chor.getServices()) {
			deployedServices.put(service.getSpec().getName(), service.getUris());
		}
	}

}

package eu.choreos.vv.util;

import br.usp.ime.choreos.vvws.common.RunServer;

public class WebServiceController {

	public static void deployService() {
		RunServer.runServers();
	}

	public static void undeployService() {
		RunServer.killServers();
	}

}

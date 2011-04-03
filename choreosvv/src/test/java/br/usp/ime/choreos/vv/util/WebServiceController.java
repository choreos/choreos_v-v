package br.usp.ime.choreos.vv.util;

import br.usp.ime.choreos.vvws.ws.RunServer;

public class WebServiceController {

	public static void deployService() {
		RunServer.runServers();
	}

	public static void undeployService() {
		RunServer.killServers();
	}

}

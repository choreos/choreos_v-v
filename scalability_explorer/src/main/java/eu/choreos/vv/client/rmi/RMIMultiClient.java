package eu.choreos.vv.client.rmi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

import eu.choreos.vv.client.MultiClient;

public class RMIMultiClient<T extends RMIClient<A,B>, A,B> extends MultiClient<T, A, B> {

	protected void createClients(Class<T> cls, long qtd, List<String> remotes)
			throws InstantiationException, IllegalAccessException, RemoteException, NotBoundException {
		clients = new ArrayList<T>();
		int n = remotes.size();
		int r = 0;
		String name = "Client";
		for (int i = 0; i < qtd; i++) {
			Registry registry = LocateRegistry.getRegistry(remotes.get(r));
			r = (r+1) % n;
			T client = (T) registry.lookup(name);
			clients.add(client);
		}
	}

}

package eu.choreos.vv.client.rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public abstract class BaseRMIClient<K, T> implements RMIClient<K, T> {

	public BaseRMIClient() {
		super();
	}

	public static <K,T> void publish(RMIClient<K,T> remote) throws RemoteException  {
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		String name = "Client";
		RMIClient stub = (RMIClient) UnicastRemoteObject.exportObject(remote, 0);
		Registry registry = LocateRegistry.getRegistry();
		registry.rebind(name, stub);
	}

}

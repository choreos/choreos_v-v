package eu.choreos.vv.client.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

import eu.choreos.vv.client.Client;

public interface RMIClient<K,T> extends Client<K,T>, Remote {
	
	/**
	 * This method must be overridden in order to execute the proper request
	 * 
	 * @throws RemoteException
	 */
	T request(K param) throws RemoteException;

	

}

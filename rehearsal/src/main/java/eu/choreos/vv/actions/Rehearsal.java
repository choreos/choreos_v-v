package eu.choreos.vv.actions;

import com.eviware.soapui.SoapUI;

/**
 * This classes manages SoapUI threads
 * 
 * @author Felipe Besson
 *
 */
public class Rehearsal {
	
	/**
	 * Stops all SoapUI threads
	 * 
	 */
	public static void stopAll(){
		SoapUI.getThreadPool().shutdown();
		SoapUI.shutdown();
	}

}

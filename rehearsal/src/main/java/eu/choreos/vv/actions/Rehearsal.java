package eu.choreos.vv.actions;

import com.eviware.soapui.SoapUI;

public class Rehearsal {
	
	public static void stopAll(){
		SoapUI.getThreadPool().shutdown();
		SoapUI.shutdown();
	}

}

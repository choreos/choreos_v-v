package eu.choreos.vv.common;

import java.util.regex.Pattern;

/**
 * This provides features for formating WSDL URIs
 * 
 * @author Felipe Besson
 *
 */
public class WsdlUtils {

	public static String getBaseName(String wsdlUri) {
		String baseName = "";
		Pattern pattern = Pattern.compile("/");
		String items[] = pattern.split(wsdlUri);
		
		int hostNamePosition = 0;
		
		for(int i =0; i< items.length;  i++){
			if(items[i].isEmpty())
				hostNamePosition = i;
		}
		
		for(int i=hostNamePosition + 2; i<items.length-1; i++)
			baseName += items[i] + "/";

		pattern = Pattern.compile("[?.]wsdl");
		baseName = baseName +  pattern.split(items[items.length-1])[0];
		
			return baseName;
	}

}

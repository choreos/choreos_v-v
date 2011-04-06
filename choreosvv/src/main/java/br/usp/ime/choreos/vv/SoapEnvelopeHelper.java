package br.usp.ime.choreos.vv;

import java.util.Arrays;
import java.util.List;

public class SoapEnvelopeHelper {

	private static String getTag(int i){
		return "<arg" + i + ">";
	}
	
	public static String generate(String xml, List<String> parameters) throws IllegalArgumentException  {
		if(xml.matches(".*" + getTag(parameters.size()) + ".*")){

			IllegalArgumentException lessParameters = new  IllegalArgumentException(
					"Number of parameters less than number of parameters in XML envelope. "
							+ "Parameters given: "
							+ parameters.size());
			throw lessParameters;
		}

		for(int i = 0; i < parameters.size(); i++){
			// Tag for the current argument
			String currentArgTag = getTag(i);
			String argRegex = currentArgTag + "\\?";
			if(xml.indexOf(currentArgTag) < 0){
				IllegalArgumentException noMoreParameters = new IllegalArgumentException (
						"Number of parameters exceeds number of parameters in XML envelope. Parameters expected: "
								+ (i - 1)
								+ " parameters given: "
								+ parameters.size());
				throw noMoreParameters;
			}
			xml = xml.replaceFirst(argRegex, currentArgTag + parameters.get(i));
		}
		
		return xml;
	}
	
	public static String generate(String xml, String... parameters) throws Exception {
		List<String> paramList = Arrays.asList(parameters);
		
		return generate(xml, paramList);
	}
	
}

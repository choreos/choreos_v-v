package br.usp.ime.choreos.vv;

import java.util.List;

public class EnvelopeArgumentGenerator {

	private static String getTag(int i){
		return "<arg" + i + ">";
	}
	
	public static String generate(String xml, List<String> parameters) throws Exception {

		if(xml.matches(".*" + getTag(parameters.size()) + ".*")){
			Exception lessParameters = new Exception(
					"Number of parameters less than number of parameters in XML envelope. "
							+ "Parameters given: "
							+ parameters.size());
			throw lessParameters;
		}

		for(int i = 0; i < parameters.size(); i++){
			// Tag for the current argument
			String currentArgTag = getTag(i);
			String argRegex = currentArgTag + "\\?";
			if(!xml.matches(".*" + argRegex + ".*")){
				Exception noMoreParameters = new Exception(
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
}

package br.usp.ime.choreos;

import java.util.List;

public class EnvelopeArgumentGenerator {

	public static String generate(String xml, List<String> parameters) throws Exception {

		for(int i = 0; i < parameters.size(); i++){
			// Tag for the current argument
			String currentArgTag = "<arg" + i + ">";
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

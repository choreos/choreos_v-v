package eu.choreos.vv.servicesimulator;

import java.util.ArrayList;
import java.util.List;

import eu.choreos.vv.exceptions.ParserException;

public class ScriptBuilder {

	private String script; 
	private String defaultRequest;
	private String defaultResponse;
	private List<MockResponse> responses;
	
	public ScriptBuilder(){
		script = "def request = new XmlSlurper().parseText(mockRequest.requestContent)" + "\n";
		responses = new ArrayList<MockResponse>();
	}
	
	public String getScript() throws ParserException {
		return script + createConditionStatements();
	}

	public void addConditionFor(MockResponse response) {
		MockResponse removed = null;
		for (MockResponse entry : responses) {
			if(entry.equals(response)){
				removed = entry;
				break;
			}
		}
		
		responses.remove(removed);
		responses.add(response);
	}

	public void setDefaultRequest(String defaultRequest) {
		this.defaultRequest = defaultRequest;
	}

	public void setDefaultResponse(String defaultResponse) {
		this.defaultResponse = defaultResponse;
	}

	
	private String createConditionStatements() throws ParserException{
		String conditions = "";
		for (MockResponse entry : responses) {
			conditions += "if ( request == new XmlSlurper().parseText('''" + entry.buildRequestContent(defaultRequest) +"'''))" + "\n"+
										"context.message = '''" + entry.buildResponseContent(defaultResponse) + "'''" +"\n";
		}
		
		return conditions;
	}

}

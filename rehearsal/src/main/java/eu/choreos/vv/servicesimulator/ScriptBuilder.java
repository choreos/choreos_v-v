package eu.choreos.vv.servicesimulator;

import java.util.ArrayList;
import java.util.List;

import eu.choreos.vv.exceptions.ParserException;

/**
 * This class builds the script which is used to apply the WSMock dynamic behavior 
 * 
 * @author Felipe Besson
 *
 */
 class ScriptBuilder {

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
		String wildCard = "";
		
		for (MockResponse entry : responses) {
			
			if(entry.getRequestParam()!=null && entry.getRequestParam()[0].equals("*"))
				wildCard = "context.message = '''" + entry.buildResponseContent(defaultResponse) + "'''";
			else
			conditions += "if ( request == new XmlSlurper().parseText('''" + entry.buildRequestContent(defaultRequest) +"'''))" + "\n"+
										"context.message = '''" + entry.buildResponseContent(defaultResponse) + "'''" +"\n";
		}
		
		if (!wildCard.isEmpty()){
			if (responses.size() == 1)
				return wildCard;
			
			conditions += "else" + "\n" + wildCard;
		}
		
		return conditions;
	}

}

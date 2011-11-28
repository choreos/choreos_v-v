package eu.choreos.vv.interceptor;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import eu.choreos.vv.common.WsdlUtils;

public class WsdlUtilsTest {
	
	@Test
	public void shouldRetrieveTheBaseNameWhenItIsAWord() throws Exception {
		String wsdlUri = "http://localhost:1234/service?wsdl";
		assertEquals("service", WsdlUtils.getBaseName(wsdlUri));
	}
	
	@Test
	public void shouldRetrieveTheBaseNameWhenItIsWords() throws Exception {
		String wsdlUri = "http://localhost:1234/ws/service?wsdl";
		assertEquals("ws/service", WsdlUtils.getBaseName(wsdlUri));
	}
	
	@Test
	public void shouldRetrieveTheBaseNameWhenHaveDots() throws Exception {
		String wsdlUri = "http://futureMart/web.service?wsdl";
		assertEquals("web.service", WsdlUtils.getBaseName(wsdlUri));
	}
	
	@Test
	public void shouldRetrieveTheBaseNameWhenHaveDotsAndComplexNames() throws Exception {
		String wsdlUri = "http://futureMart/wsdl/br/web.service?wsdl";
		assertEquals("wsdl/br/web.service", WsdlUtils.getBaseName(wsdlUri));
	}

}

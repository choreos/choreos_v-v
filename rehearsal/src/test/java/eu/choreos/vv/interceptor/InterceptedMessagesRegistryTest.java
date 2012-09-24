package eu.choreos.vv.interceptor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class InterceptedMessagesRegistryTest {
	
	@Test
	public void messageRegistyShouldBeASingleton() throws Exception {
		InterceptedMessagesRegistry a = InterceptedMessagesRegistry.getInstance();
		InterceptedMessagesRegistry b = InterceptedMessagesRegistry.getInstance();
		
		assertNotNull(a);
		assertNotNull(b);
		assertTrue(a == b); 
	}
	
	@Test
	public void shouldReturnAMessageListWhenRegisterAWsdlInspected() throws Exception {
		InterceptedMessagesRegistry registry = InterceptedMessagesRegistry.getInstance();
		registry.registerWsdl("a wsdl");
		
		assertNotNull(registry.getMessages("a wsdl"));
	}
	
	@Test
	public void shouldRemoveAWsdlInspected() throws Exception {
		InterceptedMessagesRegistry registry = InterceptedMessagesRegistry.getInstance();
		registry.registerWsdl("a wsdl");
		registry.removeWsdl("a wsdl");
		
		assertNull(registry.getMessages("a wsdl"));
	}
	
	@Test
	public void shouldRetrieveAMessageIntercepted() throws Exception {
		InterceptedMessagesRegistry registry = InterceptedMessagesRegistry.getInstance();
		registry.registerWsdl("a wsdl");
		registry.addMessage("a wsdl", "a xml message");
		
		assertEquals("a xml message", registry.getMessages("a wsdl").get(0));
	}
	
	@Test
	public void shouldRetrieveMessagesIntercepted() throws Exception {
		InterceptedMessagesRegistry registry = InterceptedMessagesRegistry.getInstance();
		registry.registerWsdl("a wsdl");
		registry.addMessage("a wsdl", "a xml message");
		registry.addMessage("a wsdl", "another xml message");
		registry.addMessage("a wsdl", "another xml message");
		
		assertEquals("a xml message", registry.getMessages("a wsdl").get(0));
		assertEquals("another xml message", registry.getMessages("a wsdl").get(1));
		assertEquals("another xml message", registry.getMessages("a wsdl").get(2));
	}

	@Test
	public void whenRegisterAnExistentWsdlTheOldMessagesShouldBeErased() throws Exception {
		InterceptedMessagesRegistry registry = InterceptedMessagesRegistry.getInstance();
		registry.registerWsdl("a wsdl");
		registry.addMessage("a wsdl", "a xml message");
		
		registry.registerWsdl("a wsdl");
		registry.addMessage("a wsdl", "a new xml message");

		assertEquals("a new xml message", registry.getMessages("a wsdl").get(0));
	}
}

package eu.choreos.vv;

import java.util.HashMap;
import java.util.Map;

import com.jayway.restassured.RestAssured;

/**
 * RSClient is just a wrapper for the RESTAssured (v1.2.1) library
 * for REST communication. 
 * 
 * @author leonardo, piva
 *
 */
public class RSClient {

	private String basePath;
	private String baseUri;
	private int port;
	
	/**
	 * Creates a RSClient object.
	 * 
	 * @param baseUri The URI where the service is hosted.
	 * @param port The port where the service can be called.
	 */
	public RSClient(String baseUri, String basePath, int port){
		this.baseUri = baseUri;
		this.basePath = basePath;
		this.port = port;
	}

	/**
	 * We set the request URL every time we make a request,
	 * to avoid service calls to wrong url's. For example, 
	 * if the client creates two RSClient's pointing to two
	 * different services, the RESTAssured library will always
	 * point to the last called service, because URI and port
	 * are static attributes.
	 */
	private void setRequestUrl(){
		RestAssured.baseURI = baseUri;
		RestAssured.basePath = basePath;
		RestAssured.port = port;
	}
	
	/**
	 * 
	 * @param path The path after the URI where the service is hosted.
	 * @param parameters GET parameters to be passed with the request.
	 * @return The service response as a string.
	 */
	public String get(String path, Map<String, String> parameters) {
		
		setRequestUrl();
		
		return RestAssured.with().parameters(parameters).get(path).asString();

	}
	
	public String get(String path){
		return get(path, new HashMap<String, String>());
	}

	/**
	 * 
	 * @param path The path after the URI where the service is hosted.
	 * @param parameters POST parameters to be passed with the request.
	 * @return The service response as a string.
	 */
	public String post(String path, Map<String, String> parameters) {

		setRequestUrl();
		
		return RestAssured.with().parameters(parameters).post(path).asString();

	}
	
	public String post(String path){
		return post(path, new HashMap<String, String>());
	}

	/**
	 * 
	 * @param path The path after the URI where the service is hosted.
	 * @param parameters PUT parameters to be passed with the request.
	 * @return The service response as a string.
	 */
	public String put(String path, Map<String, String> parameters) {

		setRequestUrl();
		
		return RestAssured.with().parameters(parameters).put(path).asString();

	}
	
	public String put(String path){
		return put(path, new HashMap<String, String>());
	}

	/**
	 * 
	 * @param path The path after the URI where the service is hosted.
	 * @param parameters DELETE parameters to be passed with the request.
	 * @return The service response as a string.
	 */
	public String delete(String path, Map<String, String> parameters) {
		
		setRequestUrl();
		
		return RestAssured.with().parameters(parameters).delete(path).asString();

	}
	
	public String delete(String path){
		return delete(path, new HashMap<String, String>());
	}

}

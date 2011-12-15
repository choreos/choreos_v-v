package eu.choreos.vv.common;

import java.io.IOException;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

/**
 * This class provides feature for managing the HTTP requests and responses
 * 
 * @author Felipe Besson
 *
 */
public class HttpUtils {

	public static boolean verifyIfUriReturns0kforGET(String url) throws HttpException, IOException{
		HttpClient client = new HttpClient();
		GetMethod method = new GetMethod(url);
		
		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, 
				    		new DefaultHttpMethodRetryHandler(3, false));
		
		int  statusCode = client.executeMethod(method); 
				
                method.releaseConnection();
                 
		return (statusCode == HttpStatus.SC_OK);
	}
	
	public static boolean UriAreUsed(String url) {
		HttpClient client = new HttpClient();
		GetMethod method = new GetMethod(url);
		
		method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, 
				    		new DefaultHttpMethodRetryHandler(3, false));
		try {
			client.executeMethod(method);

			if (method.getResponseBodyAsString().isEmpty())
				return true;
			
		} catch (HttpException e) {
			return false;
		} catch (IOException e) {
			return false;
		} 
		 method.releaseConnection();
		 
		 return false;
		
	}
	
	public static void main(String[] args) {
		UriAreUsed("http://localhost:8088");
	}
	
	
}

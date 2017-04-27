package eu.choreos.vv.client;

import java.util.Map;

import eu.choreos.vv.chart.Labeled;
import eu.choreos.vv.data.ReportData;

public interface Client<K, T> extends Labeled {

	ReportData execute(int numberOfCalls, long delay, Map<String, Object> params) throws Exception;

	/**
	 * This method can be overridden to execute before each Iteration
	 * 
	 * @throws Exception
	 */
	void setUp() throws Exception;

	/**
	 * This method can be overriden to execute after each Iteration
	 * 
	 * @throws Expeption
	 */
	void tearDown() throws Exception;

	/**
	 * This method can be overridden to execute before each request
	 * 
	 * @throws Exception
	 */
	K beforeRequest() throws Exception;

	/**
	 * This method must be overridden in order to execute the proper request
	 * 
	 * @throws Exception
	 */
	T request(K param) throws Exception;

	/**
	 * This method can be overriden to execute after each request
	 * 
	 * @throws Exception
	 */
	void afterRequest(T param) throws Exception;

}
package eu.choreos.vv.abstractor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import eu.choreos.vv.exceptions.NonJUnitTestCasesException;

public class Role {

	private String name;
	private String wsdl;
	
	public Role(String name, String wsdl) {
		this.name = name;
		this.wsdl = wsdl;
        }

	public boolean runTests(Class<?> testClass) throws NonJUnitTestCasesException {	
		
		if(!isCompatibleWithTests(testClass))
			throw new NonJUnitTestCasesException("Test case cannot be in found in " + testClass); 
		
		ConformanceTestCase.setName(wsdl);
		Class<?> claz = testClass.asSubclass(ConformanceTestCase.class);
		
		Result result = org.junit.runner.JUnitCore.runClasses(claz);
		
		if (result.getFailureCount()<=0)
			return true;
		
		String out = formatStackTrace(result);
		
		throw new AssertionError(out);
        }

	private String formatStackTrace(Result result) {
	        String out = "";
		for (Failure failure : result.getFailures()) {
	                out += failure.getTrace().toString() + "\n";
                }
	        return out;
        }

	public static boolean isCompatibleWithTests(Class<?> testCases) {
	        
		for(Method method : testCases.getDeclaredMethods()){
			
			for(Annotation annotation : method.getAnnotations())
				if(annotation.toString().startsWith("@org.junit.Test"))
					return true;
		}
		
	        return false;
        }
	
	

}

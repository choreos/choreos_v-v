package eu.choreos.vv.assertions;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import junit.framework.Assert;

import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import eu.choreos.vv.abstractor.Role;
import eu.choreos.vv.abstractor.Service;
import eu.choreos.vv.exceptions.NonJUnitTestCasesException;
import eu.choreos.vv.abstractor.ComplianceTestCase;

/**
 * This class provides the Rehearsal Assertions
 * 
 * @author Felipe Besson
 *
 */
public class RehearsalAsserts extends Assert{
	
	/**
	 * Asserts if the a service is playing a role correctly
	 * 
	 * @param aRole is the reference role
	 * @param aService is the service under test
	 * @param testCases are the test applied in the service to verifiy its role compliance
	 * @throws NonJUnitTestCasesException
	 */
	public static  void assertRole(Role aRole, Service aService, Class<?> testCases) throws NonJUnitTestCasesException{
		if (!aService.getRoles().contains(aRole))
			fail("The service does not implements " + aRole.getName());
		
		if (!isCompatibleWithTests(testCases)){
			throw new NonJUnitTestCasesException("No @Test was found");
		}
		
		ComplianceTestCase.setName(aService.getUri());
		Class<?> claz = testCases.asSubclass(ComplianceTestCase.class);

		Result result = org.junit.runner.JUnitCore.runClasses(claz);

		if (result.getFailureCount()>0){
			String out = formatStackTrace(result);
			fail(out);
		}
	}
	
	private static String formatStackTrace(Result result) {
	        String out = "";
		for (Failure failure : result.getFailures()) {
	                out += failure.getTrace().toString() + "\n";
                }
	        return out;
        }
	
	private static boolean isCompatibleWithTests(Class<?> testCases) {

		for(Method method : testCases.getDeclaredMethods()){

			for(Annotation annotation : method.getAnnotations())
				if(annotation.toString().startsWith("@org.junit.Test"))
					return true;
		}

	        return false;
        }

}

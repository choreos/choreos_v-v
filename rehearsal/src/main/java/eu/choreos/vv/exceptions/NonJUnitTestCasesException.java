package eu.choreos.vv.exceptions;

/**
 * This Exception is thrown if the a Non Junit is used to implement
 * the compliance test case
 * 
 * @author Felipe Besson
 */
public class NonJUnitTestCasesException extends Exception{
	
        private static final long serialVersionUID = 1L;

	public NonJUnitTestCasesException(String message){
		super(message);
	}

}

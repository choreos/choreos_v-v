package eu.choreos.vv.exceptions;

public class NonJUnitTestCasesException extends Exception{
	
	/**
         * This Exception is thrown if the a Non Junit is used to implement
         * the compliance test case
         * 
         */
        private static final long serialVersionUID = 1L;

	public NonJUnitTestCasesException(String message){
		super(message);
	}

}

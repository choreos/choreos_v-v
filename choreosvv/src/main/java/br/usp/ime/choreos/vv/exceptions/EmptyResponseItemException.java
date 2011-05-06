package br.usp.ime.choreos.vv.exceptions;

/**
 * This exception is thrown if user tries to access a member of response item
 * when the response item is a result of a void web service operation
 * 
 * @author besson, leonardo
 *
 */
public class EmptyResponseItemException extends Exception {

        private static final long serialVersionUID = 1299732141624501823L;

        public EmptyResponseItemException(String message){
                super(message);
        }

}

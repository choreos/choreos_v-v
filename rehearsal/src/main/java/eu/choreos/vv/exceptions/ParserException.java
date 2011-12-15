package eu.choreos.vv.exceptions;

/**
 * This exception is thrown when a parser problem occurs during the XML
 * processing
 * 
 * 
 * @author besson
 *
 */
public class ParserException extends FrameworkException{

        public ParserException(Exception originalException) {
                super(originalException);
        }

        /**
         * 
         */
        private static final long serialVersionUID = 1L;

}

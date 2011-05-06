package br.usp.ime.choreos.vv.exceptions;

public class MissingResponseTagException extends Exception  {
        
        private static final long serialVersionUID = 1L;

        public MissingResponseTagException(String message){
                super(message);
        }

}

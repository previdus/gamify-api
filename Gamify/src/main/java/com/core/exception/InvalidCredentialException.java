/**
 * 
 */
package com.core.exception;

/**
 * @author Amit
 */
public class InvalidCredentialException extends Exception {

      /**
	 * 
	 */
      private static final long serialVersionUID = 1L;

      public InvalidCredentialException() {
            super();
      }

      public InvalidCredentialException(final Throwable e) {
            super(e);
      }
}

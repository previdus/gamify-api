package com.core.exception;

/**
 * @author Vijay
 */
public class InsufficientRightsException extends Exception {

      private static final long serialVersionUID = 1L;

      /**
       * Throws an Exception with Default Message
       */
      public InsufficientRightsException() {
            super("Insufficient Rights to perform Action");
      }

      /**
       * Throws an Exception with specified Message
       * 
       * @param msg
       *              - Exception Message
       */
      public InsufficientRightsException(final String msg) {
            super(msg);
      }
}

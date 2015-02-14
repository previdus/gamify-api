package com.core.exception;

public class EmailSendingFailException extends Exception {

      private static final long serialVersionUID = 1L;

      public EmailSendingFailException() {
            super();
      }

      public EmailSendingFailException(final Throwable e) {
            super(e);
      }

}

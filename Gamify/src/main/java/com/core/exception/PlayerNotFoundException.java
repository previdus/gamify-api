package com.core.exception;

public class PlayerNotFoundException extends Exception {

      private static final long serialVersionUID = 1L;

      public PlayerNotFoundException() {
            super();
      }

      public PlayerNotFoundException(final Throwable e) {
            super(e);
      }
}

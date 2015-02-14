package com.core.exception;

public class RepositoryException extends RuntimeException {

      private static final long serialVersionUID = 1L;
      @SuppressWarnings("unused")
      private final String message = null;

      public RepositoryException() {}

      public RepositoryException(final Exception ex) {
            super(ex);
      }

      public RepositoryException(final String message, final Exception ex) {
            super(message, ex);
      }
}

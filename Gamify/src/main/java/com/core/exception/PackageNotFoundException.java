package com.core.exception;

public class PackageNotFoundException extends Exception {

      private static final long serialVersionUID = 1L;

      public PackageNotFoundException() {
            super();
      }

      public PackageNotFoundException(final Throwable e) {
            super(e);
      }
}

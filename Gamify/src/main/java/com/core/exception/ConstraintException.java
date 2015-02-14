package com.core.exception;

public class ConstraintException extends RepositoryException {
      private static final long serialVersionUID = 1L;

      private String constraintName;
      private String constraintType;

      public ConstraintException() {}

      public ConstraintException(final Exception ex) {
            super(ex);
      }

      public ConstraintException(final String message, final Exception ex) {
            super(message, ex);
      }

      public String getConstraintName() {
            return constraintName;
      }

      public void setConstraintName(final String constraintName) {
            this.constraintName = constraintName;
      }

      public String getConstraintType() {
            return constraintType;
      }

      public void setConstraintType(final String constraintType) {
            this.constraintType = constraintType;
      }
}

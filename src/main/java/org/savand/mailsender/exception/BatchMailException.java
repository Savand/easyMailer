package org.savand.mailsender.exception;

public class BatchMailException extends Exception {


  private static final long serialVersionUID = 1L;

  public BatchMailException() {
  }

  public BatchMailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public BatchMailException(String message, Throwable cause) {
    super(message, cause);
  }

  public BatchMailException(String message) {
    super(message);
  }

  public BatchMailException(Throwable cause) {
    super(cause);
  }

  
}

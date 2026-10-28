package com.thangamfrm.playwrightjavaautomation.exception;

public class TestAutomationException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  public TestAutomationException() {
    super();
  }

  public TestAutomationException(String cause) {
    super(cause);
  }

  public TestAutomationException(String cause, Throwable throwable) {
    super(cause, throwable);
  }

}

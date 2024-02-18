package com.exam.exception;

public class TestNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public TestNotFoundException() {
		super();
	}

	public TestNotFoundException(String message) {
		super(message);
	}
	 public TestNotFoundException(String message, Throwable cause) {
	        super(message, cause);
	    }
	 public TestNotFoundException(Throwable cause) {
	        super(cause);
	    }
	  protected TestNotFoundException(String message, Throwable cause,
              boolean enableSuppression,
              boolean writableStackTrace) {
super(message, cause, enableSuppression, writableStackTrace);
}

}

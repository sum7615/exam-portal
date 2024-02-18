package com.exam.exception;

public class TestAlreadyExistException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	 public TestAlreadyExistException() {
	        super();
	    }
	 public TestAlreadyExistException(String message) {
	        super(message);
	    }
	 public TestAlreadyExistException(String message, Throwable cause) {
	        super(message, cause);
	    }
	 public TestAlreadyExistException(Throwable cause) {
	        super(cause);
	    }
	 protected TestAlreadyExistException(String message, Throwable cause,
             boolean enableSuppression,
             boolean writableStackTrace) {
super(message, cause, enableSuppression, writableStackTrace);
}

}

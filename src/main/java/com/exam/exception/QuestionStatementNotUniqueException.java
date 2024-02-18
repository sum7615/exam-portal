package com.exam.exception;

public class QuestionStatementNotUniqueException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	 public QuestionStatementNotUniqueException() {
	        super();
	    }
	 
	 public QuestionStatementNotUniqueException(String message) {
	        super(message);
	    }
	 
	 public QuestionStatementNotUniqueException(String message, Throwable cause) {
	        super(message, cause);
	    }
	 public QuestionStatementNotUniqueException(Throwable cause) {
	        super(cause);
	    }
	 protected QuestionStatementNotUniqueException(String message, Throwable cause,
             boolean enableSuppression,
             boolean writableStackTrace) {
super(message, cause, enableSuppression, writableStackTrace);
}

}

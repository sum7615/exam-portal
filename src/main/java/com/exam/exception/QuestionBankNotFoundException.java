package com.exam.exception;

public class QuestionBankNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;
	public QuestionBankNotFoundException() {
	        super();
	    }
	 public QuestionBankNotFoundException(String message) {
	        super(message);
	    }
	 
	 public QuestionBankNotFoundException(String message, Throwable cause) {
	        super(message, cause);
	    }
	 
	  public QuestionBankNotFoundException(Throwable cause) {
	        super(cause);
	    }
	  protected QuestionBankNotFoundException(String message, Throwable cause,
              boolean enableSuppression,
              boolean writableStackTrace) {
super(message, cause, enableSuppression, writableStackTrace);
}
}

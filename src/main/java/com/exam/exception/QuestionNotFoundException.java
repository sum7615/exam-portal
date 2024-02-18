package com.exam.exception;

public class QuestionNotFoundException extends Exception{
	private static final long serialVersionUID = 1L;
	public QuestionNotFoundException() {
        super();
    }
 public QuestionNotFoundException(String message) {
        super(message);
    }
 
 public QuestionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
 
  public QuestionNotFoundException(Throwable cause) {
        super(cause);
    }
  protected QuestionNotFoundException(String message, Throwable cause,
          boolean enableSuppression,
          boolean writableStackTrace) {
super(message, cause, enableSuppression, writableStackTrace);
}

}

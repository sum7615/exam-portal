package com.exam.exception;

public class UserAlreadyExistException extends Exception{
	private static final long serialVersionUID = 1L;
	public UserAlreadyExistException(String msg) {
		super(msg);
	}
	   public UserAlreadyExistException() {
	        super();
	    }
	   public UserAlreadyExistException(String message, Throwable cause) {
	        super(message, cause);
	    }
	   public UserAlreadyExistException(Throwable cause) {
	        super(cause);
	    }
	    protected UserAlreadyExistException(String message, Throwable cause,
                boolean enableSuppression,
                boolean writableStackTrace) {
super(message, cause, enableSuppression, writableStackTrace);
}

}

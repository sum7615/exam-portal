package com.exam.exception;

public class InvalidParamException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public InvalidParamException(String msg) {
		super(msg);
	}

}

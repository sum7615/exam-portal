package com.exam.exception;

public class ReportGenerationException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ReportGenerationException(String message) {
        super(message);
    }
}
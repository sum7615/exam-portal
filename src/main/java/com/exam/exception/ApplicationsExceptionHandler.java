package com.exam.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
@ControllerAdvice
public class ApplicationsExceptionHandler {
	
	@ExceptionHandler(TransactionSystemException.class)
	public ResponseEntity<List<String>> handleTransactionSystemException(TransactionSystemException ex) {
	    Throwable cause = ex.getRootCause();
	    if (cause instanceof ConstraintViolationException) {
	        ConstraintViolationException constraintViolationException = (ConstraintViolationException) cause;
	        Set<ConstraintViolation<?>> constraintViolations = constraintViolationException.getConstraintViolations();
	        List<String> errors = new ArrayList<>();
	        for (ConstraintViolation<?> constraintViolation : constraintViolations) {
	            String field = constraintViolation.getPropertyPath().toString();
	            String message = constraintViolation.getMessage();
	            errors.add(field + ": " + message);
	        }
	        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	    }
	    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
	    BindingResult result = ex.getBindingResult();
	    List<FieldError> fieldErrors = result.getFieldErrors();
	    StringBuilder errorMessage = new StringBuilder();
	    for (FieldError fieldError : fieldErrors) {
	        String fieldName = fieldError.getField();
	        String errorMessageText = fieldError.getDefaultMessage();
	        errorMessage.append(fieldName).append(": ").append(errorMessageText).append("\n");
	    }
	    return ResponseEntity.badRequest().body(errorMessage.toString());
	}


}

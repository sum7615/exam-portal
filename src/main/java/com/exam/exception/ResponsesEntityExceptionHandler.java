package com.exam.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.exam.dto.ErrorMessage;

@RestControllerAdvice
public class ResponsesEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(QuestionBankNotFoundException.class)
	public ResponseEntity<ErrorMessage> questionBankNotFoundException(QuestionBankNotFoundException exception,
	        WebRequest request) {
	    ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
	    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
	}


	@ExceptionHandler(QuestionNotFoundException.class)
	public ResponseEntity<ErrorMessage> questionNotFoundException(QuestionNotFoundException exception,
			WebRequest request) {
		ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
	}

	@ExceptionHandler(TestNotFoundException.class)
	public ResponseEntity<ErrorMessage> testNotFoundException(TestNotFoundException exception, WebRequest request) {
		ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
	}

	@ExceptionHandler(UserAlreadyExistException.class)
	public ResponseEntity<ErrorMessage> useralreadyExistException(UserAlreadyExistException exception,
			WebRequest request) {
		ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorMessage> userNotfoundException(UserNotFoundException exception, WebRequest request) {
		ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
	}

	@ExceptionHandler(TestAlreadyExistException.class)
	public ResponseEntity<ErrorMessage> testalreadyexistexception(TestAlreadyExistException exception,
			WebRequest request) {
		ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
	}

	@ExceptionHandler(TestNotStartedException.class)
	public ResponseEntity<ErrorMessage> testnotstartedexception(TestNotStartedException exception, WebRequest request) {
		ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
	}

	@ExceptionHandler(QuestionStatementNotUniqueException.class)
	public ResponseEntity<ErrorMessage> questionstatementnotuniqueexception(
			QuestionStatementNotUniqueException exception, WebRequest request) {
		ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
	}
	@ExceptionHandler(ReportGenerationException.class)
	public ResponseEntity<ErrorMessage> reportgenerationexception(
			ReportGenerationException exception, WebRequest request) {
		ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND, exception.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(message);
	}
}

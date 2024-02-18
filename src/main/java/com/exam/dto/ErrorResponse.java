package com.exam.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;




public class ErrorResponse {
    private HttpStatus status;
    private String message;
    private List<String> errors;

    public ErrorResponse(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
        this.errors = new ArrayList<>();
    }

    public ErrorResponse(HttpStatus status, String message, List<String> errors) {
    
        this.status = status;
        this.message = message;
        this.errors = errors;
    }
    public ErrorResponse(HttpStatus status, String message, String error) {
        this.status = status;
        this.message = message;
        this.errors = new ArrayList<>();
        this.errors.add(error);
    }

     

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}


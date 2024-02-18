package com.exam.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.exam.dto.QuestionBankDto;
import com.exam.entity.QuestionBank;
import com.exam.exception.QuestionBankNotFoundException;
import com.exam.service.QuestionBankService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
@RestController
public class QuestionBankController {
	
	@Autowired
	public QuestionBankService questionBankService;
	
	Logger log = LoggerFactory.getLogger(QuestionBankController.class);
	
	
	
	@SecurityRequirement(name = "Bearer Authentication")
//	@PreAuthorize(value = "hasRole('ROLE_ADMIN')")s
	@PostMapping("/question-bank")
	public String add_question_bank(@Valid @RequestBody QuestionBank questionBank)throws QuestionBankNotFoundException{
		log.debug("Add question bank => Request {}", questionBank);
		String response = questionBankService.add_question_bank(questionBank);
		log.debug("Add question bank => Response {}", response);
		return response;
	}
	
	@SecurityRequirement(name = "Bearer Authentication")
	@PreAuthorize(value = "hasRole('ROLE_ADMIN')")
	@PutMapping("/question-bank")
	public ResponseEntity<String> update_question_bank(@Valid @RequestBody QuestionBank questionBank) {
	    try {
	    	log.debug("Update question bank => Request {}", questionBank);
	        ResponseEntity<String> result = questionBankService.update_question_bank(questionBank);
	    	log.debug("Update question bank => Response {}", result);
	        return ResponseEntity.ok(result.getBody());
	    } catch (NoSuchElementException ex) {
	    	log.error("Update question bank => error", ex);
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Question bank update failed. " + ex.getMessage());
	    }
	}
	@SecurityRequirement(name = "Bearer Authentication")
	@PreAuthorize(value = "hasRole('ROLE_ADMIN')")
	@DeleteMapping("/question-bank")
	public ResponseEntity<String> delete_question_bank(@RequestBody QuestionBankDto questionbank) throws QuestionBankNotFoundException {
	    try {
	        log.debug("Delete question bank => Request {}", questionbank);
	        ResponseEntity<String> result = questionBankService.delete_question_bank(questionbank.getQbid());
	        if(result.getStatusCode() == HttpStatus.OK) {
	            log.debug("Update question bank => Response {}", result);
	            return ResponseEntity.ok(result.getBody());
	        }
	        log.error("Delete question bank => error: {}", result.getBody());
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Question bank deletion failed. " + result.getBody());
	    } catch (Exception ex) {
	        log.error("Delete question bank => error ", ex);
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Question bank deletion failed. " + ex.getMessage());
	    }
	}


	@SecurityRequirement(name = "Bearer Authentication")

	@PreAuthorize(value = "hasRole('ROLE_ADMIN')")
	@GetMapping("/questionbanklist")
    public List<QuestionBankDto> list_question_bank()throws QuestionBankNotFoundException{
    	return questionBankService.list_question_bank();
    }
}

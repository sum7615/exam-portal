package com.exam.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.exam.entity.Question;
import com.exam.exception.QuestionBankNotFoundException;
import com.exam.exception.QuestionNotFoundException;
import com.exam.exception.QuestionStatementNotUniqueException;
import com.exam.service.QuestionService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
public class QuestionController {
	
	@Autowired
	private QuestionService questionService;
	
	Logger log = LoggerFactory.getLogger(QuestionBankController.class);
	
	@SecurityRequirement(name = "Bearer Authentication")
	@PreAuthorize(value = "hasRole('ROLE_ADMIN')")
	@PostMapping("/question")
	public String add_question( @Valid @RequestBody Question question) throws MethodArgumentNotValidException, QuestionBankNotFoundException {
//		return questionService.add_question(question);
		
		log.debug("Received request to add question: {}", question);
	    try {
	        String result = questionService.add_question(question);
	        log.debug("Successfully added question: {}", question);
	        return result;
	    } catch (QuestionBankNotFoundException e) {
	        log.error("Question bank not found for question: {}", question, e);
	        throw e;
	    } catch (Exception e) {
	        log.error("Error adding question: {}", question, e);
	        throw e;
	    }
		
	}
	
	@SecurityRequirement(name = "Bearer Authentication")
	@PreAuthorize(value = "hasRole('ROLE_ADMIN')")
	@PutMapping("/question")
	 public String edit_question(@Valid @RequestBody Question question) throws QuestionStatementNotUniqueException, QuestionBankNotFoundException, QuestionNotFoundException {
//		 return questionService.edit_question(question);
		log.debug("Received request to edit question: {}", question);
	    try {
	        String result = questionService.edit_question(question);
	        log.debug("Successfully edited question: {}", question);
	        return result;
	    } catch (QuestionStatementNotUniqueException e) {
	        log.error("Question statement not unique for question: {}", question, e);
	        throw e;
	    } catch (QuestionBankNotFoundException e) {
	        log.error("Question bank not found for question: {}", question, e);
	        throw e;
	    } catch (QuestionNotFoundException e) {
	        log.error("Question not found for question: {}", question, e);
	        throw e;
	    } catch (Exception e) {
	        log.error("Error editing question: {}", question, e);
	        throw e;
	    }
	 }
	
	@SecurityRequirement(name = "Bearer Authentication")
	@PreAuthorize(value = "hasRole('ROLE_ADMIN')")
	@DeleteMapping("/question")
	public String delete_question(@RequestBody Question question) throws QuestionNotFoundException {
//		return questionService.delete_question(question.getId());
		log.debug("Received request to delete question with ID: {}", question.getId());
	    try {
	        String result = questionService.delete_question(question.getId());
	        log.debug("Successfully deleted question with ID: {}", question.getId());
	        return result;
	    } catch (QuestionNotFoundException e) {
	        log.error("Question not found with ID: {}", question.getId(), e);
	        throw e;
	    } catch (Exception e) {
	        log.error("Error deleting question with ID: {}", question.getId(), e);
	        throw e;
	    }
	}
	
	@SecurityRequirement(name = "Bearer Authentication")
	@PreAuthorize(value = "hasRole('ROLE_ADMIN')")
	@PostMapping("/question-list")
	public List<Question> view_question(@RequestBody Question questionbankid) throws QuestionBankNotFoundException{
//		return questionService.view_question(questionbankid.getQuestionbankid());
		log.debug("Received request to view questions for question bank with ID: {}", questionbankid.getQuestionbankid());
	    try {
	        List<Question> result = questionService.view_question(questionbankid.getQuestionbankid());
	        log.debug("Successfully retrieved questions for question bank with ID: {}", questionbankid.getQuestionbankid());
	        return result;
	    } catch (QuestionBankNotFoundException e) {
	        log.error("Question bank not found with ID: {}", questionbankid.getQuestionbankid(), e);
	        throw e;
	    } catch (Exception e) {
	        log.error("Error retrieving questions for question bank with ID: {}", questionbankid.getQuestionbankid(), e);
	        throw e;
	    }
		
	}
}

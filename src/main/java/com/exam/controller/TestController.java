package com.exam.controller;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.exam.dto.TestDto;
import com.exam.entity.QuestionBank;
import com.exam.entity.Test;
import com.exam.exception.QuestionBankNotFoundException;
import com.exam.exception.TestAlreadyExistException;
import com.exam.exception.TestNotFoundException;
import com.exam.repository.QuestionBankRepository;
import com.exam.repository.TestRepository;
import com.exam.service.TestService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
public class TestController {
	
	@Autowired
	private QuestionBankRepository questionBankRepository;
	
	@Autowired
	private TestRepository testRepository;

	@Autowired
	public TestService testService;
	
	Logger log = LoggerFactory.getLogger(TestController.class);
	
	@SecurityRequirement(name = "Bearer Authentication")
	@PreAuthorize(value = "hasRole('ROLE_ADMIN')")
	@PostMapping("/test")
	public String testCreate(@Valid  @RequestBody TestDto testRequest) throws QuestionBankNotFoundException, TestAlreadyExistException{
		
		log.debug("Received request to create a new test: {}", testRequest);
	    
	    Long questionBankId = testRequest.getQuestionbankid();
	    if (questionBankId == null || !questionBankRepository.findById(questionBankId).isPresent()) {
	        String errorMessage = "QuestionBank ID is not valid, please provide a valid ID: " + questionBankId;
	        log.error(errorMessage);
	        throw new QuestionBankNotFoundException(errorMessage);
	    }
	    
	    if (testRepository.findById(questionBankId).isPresent()) {
	        String errorMessage = "Test already exists for question bank with ID: " + questionBankId;
	        log.error(errorMessage);
	        throw new TestAlreadyExistException(errorMessage);
	    }
	    
	    Test test = new Test();
	    QuestionBank questionBank = new QuestionBank();
	    test.setTestDate(testRequest.getTest_date_time());
	    test.setTestTime(testRequest.getTest_time());
	    test.setTestName(testRequest.getTest_Name());
	    test.setStatus(testRequest.getStatus());
	    test.setTotalScore(testRequest.getTotal_score());
	    test.setTotalTime(testRequest.getTotal_time());
	    questionBank.setQbid(testRequest.getQuestionbankid());
	    test.setQuestionBank(questionBank);

	    String result = testService.testCreate(test);
	    log.debug("Successfully created new test with ID: {}", result);
	    return result;
	}


	
	
	
	@SecurityRequirement(name = "Bearer Authentication")
	@PreAuthorize(value = "hasRole('ROLE_ADMIN')")
	@GetMapping("/test-list")
	public List<TestDto> testList() throws Exception {
		try {
	        List<TestDto> tests = testService.testList();
	        log.debug("Successfully retrieved test list");
	        return tests;
	    } catch (Exception e) {
	        log.error("Error retrieving test list: " + e.getMessage(), e);
	        throw new Exception("Error in controller");
	    }

	}
	@SecurityRequirement(name = "Bearer Authentication")
	@PreAuthorize(value = "hasRole('ROLE_ADMIN')")
	@PutMapping("/test")
	public String updateTest( @Valid @RequestBody TestDto testRequest) throws TestNotFoundException, QuestionBankNotFoundException {
		try {
	        if (testRequest.getTest_id() == null || testRepository.findById(testRequest.getTest_id()).isEmpty()) {
	            throw new TestNotFoundException("Invalid test Id , Give a valid test Id");
	        }

	        QuestionBank questionbank = questionBankRepository.findById(testRequest.getQuestionbankid())
	                .orElseThrow(() -> new QuestionBankNotFoundException("Question Bank  Not Found"));
	        Test test = new Test();
	        test.setId(testRequest.getTest_id());
	        test.setTestDate(testRequest.getTest_date_time());
	        test.setTestTime(testRequest.getTest_time());
	        test.setTestName(testRequest.getTest_Name());
	        test.setStatus(testRequest.getStatus());
	        test.setTotalScore(testRequest.getTotal_score());
	        test.setTotalTime(testRequest.getTotal_time());
	        test.setQuestionBank(questionbank);

	        String message = testService.updateTest(test);
	        log.debug("Successfully updated test with id " + testRequest.getTest_id());
	        return message;
	    } catch (Exception e) {
	        log.error("Error updating test: " + e.getMessage(), e);
	        throw e;
	    }
	}

	@SecurityRequirement(name = "Bearer Authentication")
	@PreAuthorize(value = "hasRole('ROLE_ADMIN')")
	@DeleteMapping("/test")
	public String deleteTest(@RequestBody Test test) throws TestNotFoundException {
		String logMessage = String.format("Deleting test with id %d", test.getId());
	    log.debug(logMessage); // Debug logging

	    try {
	        String result = testService.deleteTest(test.getId());
	        log.info("Test deleted successfully"); // Info logging
	        return result;
	    } catch (TestNotFoundException e) {
	        log.error("Error deleting test: " + e.getMessage()); // Error logging
	        throw new TestNotFoundException(e.getMessage());
	    }
	}
	
	
	// returns all active test
	@SecurityRequirement(name = "Bearer Authentication")
	@PreAuthorize(value = "hasRole('ROLE_ADMIN') || hasRole('ROLE_USER')")
	@GetMapping("/available-test")
	public List<String> availableTest(){
//		return testService.available_test();
		log.debug("Getting list of available tests"); // Debug logging

	    try {
	        List<String> availableTests = testService.availableTest();
	        log.info("List of available tests retrieved successfully"); // Info logging
	        return availableTests;
	    } catch (Exception e) {
	        log.error("Error retrieving list of available tests: " + e.getMessage()); // Error logging
	        throw e;
	    }
	}
}

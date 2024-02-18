package com.exam.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.exam.dto.QuestionDto;
import com.exam.dto.SingleUserReturn;
import com.exam.dto.TestManagementDto;
import com.exam.entity.Question;
import com.exam.exception.InvalidAnsException;
import com.exam.exception.QuestionNotFoundException;
import com.exam.exception.TestNotFoundException;
import com.exam.exception.TestNotStartedException;
import com.exam.exception.UserNotFoundException;
import com.exam.service.QuestionService;
import com.exam.service.TestManagementService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
public class TestManagementController {

	@Autowired
	TestManagementService testManagementService;

	@Autowired
	QuestionService questionService;

	TestManagementDto testManagementDto;
	
	Logger log = LoggerFactory.getLogger(QuestionBankController.class);

	@SecurityRequirement(name = "Bearer Authentication")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN') || hasAnyRole('ROLE_USER')")
	@PostMapping("/enroll-test")
	public String enrollTest(@RequestBody TestManagementDto testManagement) throws UserNotFoundException{
//		return testManagementService.enrollTest(testManagement.getUserid(), testManagement.getTestid());
		  long userId = testManagement.getUserid();
		    long testId = testManagement.getTestid();
		    
		    // Add debug logging
		    if (log.isDebugEnabled()) {
		        log.debug("Enrolling user {} in test {}", userId, testId);
		    }
		    
		    String result = testManagementService.enrollTest(userId, testId);
		    
		    // Add debug logging
		    if (log.isDebugEnabled()) {
		        log.debug("Result of enrolling user {} in test {}: {}", userId, testId, result);
		    }
		    
		    return result;
		}
	
	
	// returns all enrolled test name of a specific student
	@SecurityRequirement(name = "Bearer Authentication")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN') || hasAnyRole('ROLE_USER')")
	@PostMapping("/enrolled-test")
	public List<String> enrolledTest(@RequestBody TestManagementDto testManagement) throws UserNotFoundException{
//		return testManagementService.enrolledTest(testManagement.getUserid());
		  long userId = testManagement.getUserid();
		    
		    // Add debug logging
		    if (log.isDebugEnabled()) {
		        log.debug("Retrieving enrolled tests for user {}", userId);
		    }
		    
		    List<String> enrolledTests;
		    try {
		        enrolledTests = testManagementService.enrolledTest(userId);
		    } catch (UserNotFoundException ex) {
		        // Add error logging
		        log.error("User not found while retrieving enrolled tests for user {}", userId, ex);
		        throw ex;
		    } catch (Exception ex) {
		        // Add error logging
		        log.error("Error while retrieving enrolled tests for user {}", userId, ex);
		        throw ex;
		    }
		    
		    // Add debug logging
		    if (log.isDebugEnabled()) {
		        log.debug("Enrolled tests for user {}: {}", userId, enrolledTests);
		    }
		    
		    return enrolledTests;
	}

	
	// returns student name of a specific test
	@SecurityRequirement(name = "Bearer Authentication")
	@PreAuthorize(value = "hasRole('ROLE_ADMIN')")
	@PostMapping("/enrolled-student")
	public List<String> enrolledStudent(@RequestBody TestManagementDto testManagement) throws TestNotFoundException{
//		return testManagementService.enrolledStudent(testManagement.getTestid());
		long testId = testManagement.getTestid();
	    
	    log.debug("Retrieving enrolled students for test {}", testId);
	    
	    List<String> enrolledStudents;
	    try {
	        enrolledStudents = testManagementService.enrolledStudent(testId);
	        log.debug("Enrolled students for test {}: {}", testId, enrolledStudents);
	        return enrolledStudents;
	    } catch (TestNotFoundException ex) {
	        log.error("Test not found while retrieving enrolled students for test {}", testId, ex);
	        throw ex;
	    } catch (Exception ex) {
	        log.error("Error while retrieving enrolled students for test {}", testId, ex);
	        throw ex;
	    }
	}

	

	@SecurityRequirement(name = "Bearer Authentication")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN') || hasAnyRole('ROLE_USER')")
	@PostMapping("/start-test")
	public Question startTest(@RequestBody TestManagementDto testManagement) throws TestNotFoundException {
//		return testManagementService.startTest(testManagement.getTestid(), testManagement.getUserid());
		long testId = testManagement.getTestid();
	    long userId = testManagement.getUserid();
	    
	    log.debug("Starting test {} for user {}", testId, userId);
	    
	    try {
	        Question question = testManagementService.startTest(testId, userId);
	        log.debug("Started test {} for user {}. Next question: {}", testId, userId, question);
	        return question;
	    } catch (TestNotFoundException ex) {
	        log.error("Test not found while starting test {} for user {}", testId, userId, ex);
	        throw ex;
	    } catch (Exception ex) {
	        log.error("Error starting test {} for user {}", testId, userId, ex);
	        throw ex;
	    }
	}

	@SecurityRequirement(name = "Bearer Authentication")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN') || hasAnyRole('ROLE_USER')")
	@PostMapping("/submit-test")
	public String submittest(@RequestBody TestManagementDto testManagement)
			throws TestNotStartedException, TestNotFoundException {
//		return testManagementService.submittest(testManagement.getTestid(), testManagement.getUserid());
		try {
	        String result = testManagementService.submittest(testManagement.getTestid(), testManagement.getUserid());
	        log.debug("Submitted test {} for user {}", testManagement.getTestid(), testManagement.getUserid());
	        return result;
	    } catch (TestNotStartedException e) {
	        log.error("Test not started for user {} and test {}", testManagement.getUserid(), testManagement.getTestid(), e);
	        throw e;
	    } catch (TestNotFoundException e) {
	        log.error("Test not found for user {} and test {}", testManagement.getUserid(), testManagement.getTestid(), e);
	        throw e;
	    } catch (Exception e) {
	        log.error("Error submitting test for user {} and test {}", testManagement.getUserid(), testManagement.getTestid(), e);
	        throw e;
	    }
	}

	@SecurityRequirement(name = "Bearer Authentication")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN') || hasAnyRole('ROLE_USER')")
	@PostMapping("/next-question")
	public QuestionDto getNextQuestion(@RequestBody TestManagementDto testManagementDto) throws TestNotFoundException, InvalidAnsException{
//		Long questionId = testManagementDto.getQuestionid();
//		Long userId = testManagementDto.getUserid();
//		Long testId = testManagementDto.getTestid();
//		String result = testManagementDto.getResult();
//		return testManagementService.getNextQuestion(questionId, userId, testId,result);
		Long questionId = testManagementDto.getQuestionid();
	    Long userId = testManagementDto.getUserid();
	    Long testId = testManagementDto.getTestid();
	    String result = testManagementDto.getResult();
	    try {
	        QuestionDto nextQuestion = testManagementService.getNextQuestion(questionId, userId, testId, result);
	        log.debug("Retrieved next question for user {} and test {}", userId, testId);
	        return nextQuestion;
	    } catch (TestNotFoundException e) {
	        log.error("Test not found for user {} and test {}", userId, testId, e);
	        throw e;
	    } catch (InvalidAnsException e) {
	        log.error("Invalid answer provided for question {} by user {}", questionId, userId, e);
	        throw e;
	    } catch (Exception e) {
	        log.error("Error retrieving next question for user {} and test {}", userId, testId, e);
	        throw e;
	    }
	}

	@SecurityRequirement(name = "Bearer Authentication")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN') || hasAnyRole('ROLE_USER')")
	@PostMapping("/clear-response")
	public String clearResponse(@RequestBody TestManagementDto testManagement)
			throws TestNotStartedException, TestNotFoundException, QuestionNotFoundException {
		Long userid = testManagement.getUserid();
		Long testId = testManagement.getTestid();
		Long questionId = testManagement.getQuestionid();
		String result = testManagement.getResult();
//		return testManagementService.updateResponse(userid, testId, questionId, result);
		log.debug("Received request to clear response for user {}, test {}, question {}, result {}", userid, testId, questionId, result);
	    
	    try {
	        String response = testManagementService.updateResponse(userid, testId, questionId, result);
	        log.debug("Successfully cleared response for user {}, test {}, question {}, result {}", userid, testId, questionId, result);
	        return response;
	    } catch (TestNotStartedException | TestNotFoundException | QuestionNotFoundException e) {
	        log.error("Error clearing response for user {}, test {}, question {}, result {}", userid, testId, questionId, result, e);
	        throw e;
	    } catch (Exception e) {
	        log.error("Error clearing response for user {}, test {}, question {}, result {}", userid, testId, questionId, result, e);
	        throw e;
	    }
	}

	@SecurityRequirement(name = "Bearer Authentication")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN') || hasAnyRole('ROLE_USER')")
	@PostMapping("/update-response")
	public String updateResponse(@RequestBody TestManagementDto testManagement)
			throws TestNotStartedException, TestNotFoundException, QuestionNotFoundException {
		long userid = testManagement.getUserid();
		long testId = testManagement.getTestid();
		long questionId = testManagement.getQuestionid();
		String result = testManagement.getResult();
//		return testManagementService.updateResponse(userid, testId, questionId, result);
		log.debug("Received request to update response for user id: {}, test id: {}, question id: {}, result: {}", userid, testId, questionId, result);
		
		try {
			String response = testManagementService.updateResponse(userid, testId, questionId, result);
			log.debug("Successfully updated response for user id: {}, test id: {}, question id: {}", userid, testId, questionId);
			return response;
		} catch (TestNotStartedException | TestNotFoundException | QuestionNotFoundException e) {
			log.error("Error updating response for user id: {}, test id: {}, question id: {}", userid, testId, questionId, e);
			throw e;
		} catch (Exception e) {
			log.error("Error updating response for user id: {}, test id: {}, question id: {}", userid, testId, questionId, e);
			throw e;
		}

	}

	@SecurityRequirement(name = "Bearer Authentication")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN') || hasAnyRole('ROLE_USER')")
	@PostMapping("/save-response")
	public String saveResponse(@RequestBody TestManagementDto testManagementDto) throws TestNotStartedException, TestNotFoundException, QuestionNotFoundException {
		long userid = testManagementDto.getUserid();
		long testId = testManagementDto.getTestid();
		long questionId = testManagementDto.getQuestionid();
		String result = testManagementDto.getResult();
//		return testManagementService.updateResponse(userid, testId, questionId, result);
		 log.debug("Saving response for user id: {}, test id: {}, question id: {}, result: {}", userid, testId, questionId, result);

		    try {
		        String response = testManagementService.updateResponse(userid, testId, questionId, result);
		        log.debug("Response saved successfully");
		        return response;
		    } catch (Exception e) {
		        log.error("Error occurred while saving response: {}", e.getMessage());
		        throw e;
		    }
		
	}

	@SecurityRequirement(name = "Bearer Authentication")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN') || hasAnyRole('ROLE_USER')")
	@PostMapping("/previous-question")
	public QuestionDto previousQuestion(@RequestBody TestManagementDto testManagementDto) throws QuestionNotFoundException {
//		Long userid = testManagementDto.getUserid();
//		Long questionId = testManagementDto.getQuestionid();
//		return testManagementService.previousQuestion( userid, questionId);
		 try {
	            long userid = testManagementDto.getUserid();
	            long questionId = testManagementDto.getQuestionid();
	            QuestionDto question = testManagementService.previousQuestion(userid, questionId);
	            log.debug("Retrieved previous question " + question.getId() + " for user " + userid);
	            return question;
	        } catch (QuestionNotFoundException e) {
	            log.error("Failed to retrieve previous question: " + e.getMessage());
	            throw e;
	        }
	}
	
	@SecurityRequirement(name = "Bearer Authentication")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping("/attempted-student")
	public List<SingleUserReturn> attemptedStudent(@RequestBody TestManagementDto testManagementDto) throws TestNotFoundException {
//		Long testId = testManagementDto.getTestid();
//		return testManagementService.attemptedStudent(testId);
		try {
	        long testId = testManagementDto.getTestid();
	        List<SingleUserReturn> attemptedStudent = testManagementService.attemptedStudent(testId);
	        log.debug("Attempted students retrieved for test ID: {}", testId);
	        return attemptedStudent;
	    } catch (TestNotFoundException e) {
	        log.error("Error retrieving attempted students for test ID: {}", testManagementDto.getTestid(), e);
	        throw e;
	    }
	}

}

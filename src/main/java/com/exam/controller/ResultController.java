package com.exam.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.exam.dto.TestManagementDto;
import com.exam.dto.TestReportDto;
import com.exam.dto.TestReportStudentObjectDto;
import com.exam.dto.TestResultDto;
import com.exam.exception.ReportGenerationException;
import com.exam.exception.TestNotFoundException;
import com.exam.service.ResultService;
import com.exam.service.TestManagementService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
public class ResultController {
	@Autowired
	TestManagementService testManagementService;
	@Autowired
	ResultService resultService;
	Logger log = LoggerFactory.getLogger(QuestionBankController.class);
	@SecurityRequirement(name = "Bearer Authentication")
	@PreAuthorize(value = "hasRole('ROLE_ADMIN') || hasRole('ROLE_USER')")
	@PostMapping("test-report-student") // return specific test and specific student question wise analysis
	public TestReportStudentObjectDto testReportStudent(@RequestBody TestManagementDto testManagement) throws ReportGenerationException {
//		Long userId = testManagement.getUserid();
//		Long testId = testManagement.getTestid();
//		return resultService.testReportStudent(userId, testId);
		try {
	        long userId = testManagement.getUserid();
	        long testId = testManagement.getTestid();
	        TestReportStudentObjectDto testReportStudentObjectDto = resultService.testReportStudent(userId, testId);
	        log.debug("Successfully generated test report for user {} and test {}.", userId, testId);
	        return testReportStudentObjectDto;
	    } catch (ReportGenerationException e) {
	        log.error("Error generating test report for user {} and test {}: {}", testManagement.getUserid(), testManagement.getTestid(), e.getMessage());
	        throw e;
	    }
		
	}

	@SecurityRequirement(name = "Bearer Authentication")
	@PreAuthorize(value = "hasRole('ROLE_ADMIN')")
	@PostMapping("test-report") // return report specific test
		public TestReportDto testReport(@RequestBody TestManagementDto testManagement) throws TestNotFoundException{
		if(testManagement.getTestid()==null) {
			throw new TestNotFoundException("Select test to generate report");
		}
		
//		return resultService.testReport(testManagement.getTestid());
		log.debug("Generating report for test id {}", testManagement.getTestid());

	    TestReportDto report = resultService.testReport(testManagement.getTestid());

	    // Logging statements
	    log.info("Generated report for test id {}", testManagement.getTestid());
	    log.debug("Report data: {}", report);

	    return report;
	}

	@SecurityRequirement(name = "Bearer Authentication")
	@PreAuthorize(value = "hasRole('ROLE_ADMIN') || hasRole('ROLE_USER')")
	@PostMapping("/student-report") // return specific test and specific student report
	public List<TestResultDto> studentReport(@RequestBody TestManagementDto testManagement)
			throws TestNotFoundException {
		long userId = testManagement.getUserid();
		long testId = testManagement.getTestid();
//		return testManagementService.studentReport(userId, testId);
		 try {
		        List<TestResultDto> result = testManagementService.studentReport(userId, testId);
		        log.debug("Student report generated successfully for user id {} and test id {}", userId, testId);
		        return result;
		    } catch (TestNotFoundException e) {
		        log.error("Error generating student report for user id {} and test id {}: {}", userId, testId, e.getMessage());
		        throw e;
		    }
		
	}
}

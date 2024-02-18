package com.exam.serviceimpl;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exam.dto.StudentResultDto;
import com.exam.dto.TestReportDto;
import com.exam.dto.TestReportStudentDto;
import com.exam.dto.TestReportStudentObjectDto;
import com.exam.entity.Question;
import com.exam.entity.Result;
import com.exam.entity.Test;
import com.exam.entity.TestManagement;
import com.exam.exception.ReportGenerationException;
import com.exam.exception.TestNotFoundException;
import com.exam.repository.QuestionRepository;
import com.exam.repository.ResultRepository;
import com.exam.repository.TestManagementRepository;
import com.exam.repository.TestRepository;
import com.exam.repository.UserRepository;
import com.exam.service.ResultService;

@Service
public class ResultServiceimpl implements ResultService {
	@Autowired
	private TestManagementRepository managementRepository;
	@Autowired
	private QuestionRepository questionRepository;
	@Autowired
	private ResultRepository repository;
	@Autowired
	private TestRepository testRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public TestReportDto testReport(Long testid) throws TestNotFoundException {

		  Test test = testRepository.findById(testid).orElseThrow(() -> new TestNotFoundException("Test not found with ID: " + testid));
			TestReportDto dto = new TestReportDto();
			List<StudentResultDto> studentResultDtos = new ArrayList<>();
			List<TestManagement> testAttemtionList = managementRepository.findAllByTestId(testid);
			if(test==null) {
				throw new TestNotFoundException("Errore occured");
				
			}
	//		getting student id
			List<Long> studentIds = testAttemtionList.stream()
														.map(e->e.getUser().getId())
														.collect(Collectors.toList());
			for(Long i :studentIds) {
				StudentResultDto std = new StudentResultDto();
				Integer obtainedMark = testAttemtionList.stream()
				        .filter(f -> f.getUser().getId() == i)
				        .map(TestManagement::getObtainedScore)
				        .findFirst().get();
				std.setStudentName(userRepository.findById(i).get().getName());
				std.setObtainedMark(obtainedMark);
				studentResultDtos.add(std);
			}
//		Finding the count of submitted test
		int count = managementRepository.findByTestrAndStatus(testid, TestManagement.Status.SUBMITTED);
		dto.setAtttempted(count);
		dto.setEnrolled(testAttemtionList.size());
		dto.setTestName(testRepository.findById(testid).get().getTestName());
		dto.setTotalScore(testRepository.findById(testid).get().getTotalScore());
		
		// finding average time in second
		double averageTimeInSeconds = testAttemtionList.stream()
		        .map(e -> e.getTotalTimeTaken())
		        .mapToDouble(LocalTime::toSecondOfDay)
		        .average().getAsDouble();
//		converting avg second to local time
		LocalTime averageTimeTaken = LocalTime.ofSecondOfDay((long) averageTimeInSeconds);
		dto.setAverageTimeTaken(averageTimeTaken);
		        
		dto.setStudents(studentResultDtos);
		return dto;
		
	}	
	
	@Override
	public TestReportStudentObjectDto testReportStudent(Long userId, Long testId) throws ReportGenerationException {
		if (userId == null || testId == null) {
	        throw new ReportGenerationException("User ID or Test ID cannot be null");
	    }

	    TestManagement result2 = managementRepository.findByUserAndTest(userId, testId);

	    if (result2 == null) {
	        throw new ReportGenerationException("User has not attempted the test");
	    }
		TestReportStudentObjectDto dto = new TestReportStudentObjectDto();
		List<TestReportStudentDto> testReportStudentDto = new ArrayList<TestReportStudentDto>();
//		finding tests according to the user and test
		TestManagement result = managementRepository.findByUserAndTest(userId, testId);

		if (result == null) {
			throw new ReportGenerationException("User have not attempted the test");
		}

//		get all question
		List<Long> questionIdsList = result.getTest().getQuestionBank().getQuestionid();
		for (Long i : questionIdsList) {
			Result result1 = repository.findByTestAndUserAndQuestion(userId, testId, i);
			TestReportStudentDto dto2 = new TestReportStudentDto();
			Question quest = null;
			try {
				quest = questionRepository.findById(i).get();
			} catch (NoSuchElementException e) {
				// skip this question if it doesn't exist in the repository
				continue;
			}
//			Building the list 
			dto2.setStatement(quest.getStatement());
			dto2.setResponse_by_student(getQuestionOption(quest, result1.getResult()));
			dto2.setCorrect_ans(getQuestionOption(quest, quest.getCorrectans().toString()));
			testReportStudentDto.add(dto2);
		}
		// constructing returning object
		dto.setTotal_score(result.getTest().getTotalScore());
		dto.setTest_name(result.getTest().getTestName());
		dto.setTest_date(result.getTest().getTestDate());
		dto.setObtained_score(result.getObtainedScore());
		dto.setTime_taken(result.getTotalTimeTaken());
		dto.setQuestions(testReportStudentDto);

		return dto;
	}

//	By this method we fetch the value of a specific option for a specific question
	public String getQuestionOption(Question quest, String str) {
		String option1 = quest.getOption1();
		String option2 = quest.getOption2();
		String option3 = quest.getOption3();
		String option4 = quest.getOption4();
		if (str.equalsIgnoreCase("option1")) {
			return option1;
		} else if (str.equalsIgnoreCase("option2")) {
			return option2;
		} else if (str.equalsIgnoreCase("option3")) {
			return option3;
		} else if (str.equalsIgnoreCase("option4")) {
			return option4;
		} else {
			return "Wrong option selected";
		}
	}

}

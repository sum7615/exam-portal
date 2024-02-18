package com.exam.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.exam.dto.QuestionDto;
import com.exam.dto.SingleUserReturn;
import com.exam.entity.Question;
import com.exam.dto.TestResultDto;
import com.exam.exception.InvalidAnsException;
import com.exam.exception.QuestionNotFoundException;
import com.exam.exception.TestNotFoundException;
import com.exam.exception.TestNotStartedException;
import com.exam.exception.UserNotFoundException;

@Service
public interface TestManagementService {

	List<String> enrolledTest(Long userId) throws UserNotFoundException;

	String enrollTest(Long userid, Long testid) throws UserNotFoundException;

	List<String> enrolledStudent(Long testId)throws TestNotFoundException;

	Question startTest(Long testid, long userid) throws TestNotFoundException;

	String submittest(Long testid, long userid) throws TestNotStartedException, TestNotFoundException;

	QuestionDto getNextQuestion(Long questionId, Long userId, Long testId, String result) throws TestNotFoundException, InvalidAnsException;

	String updateResponse(Long userid, Long testId, Long questionId, String result)
			throws TestNotStartedException, TestNotFoundException, QuestionNotFoundException;

	List<TestResultDto> studentReport(Long userid, Long testId) throws TestNotFoundException;

	QuestionDto previousQuestion(Long userId, Long questionId)  throws QuestionNotFoundException;
	
	public List<SingleUserReturn> attemptedStudent(Long id) throws TestNotFoundException;
	
}

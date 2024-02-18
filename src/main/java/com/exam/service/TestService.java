package com.exam.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.exam.dto.TestDto;
import com.exam.entity.Test;
import com.exam.exception.QuestionBankNotFoundException;
import com.exam.exception.TestAlreadyExistException;
import com.exam.exception.TestNotFoundException;

@Service
public interface TestService {

	String testCreate(Test test) throws QuestionBankNotFoundException, TestAlreadyExistException;

	List<TestDto> testList();

	String updateTest(Test test)throws TestNotFoundException,QuestionBankNotFoundException;

	String deleteTest(Long testId)throws TestNotFoundException;

	List<String> availableTest();

}
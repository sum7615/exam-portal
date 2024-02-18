package com.exam.serviceimpl;

import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.exam.dto.TestDto;
import com.exam.entity.QuestionBank;
import com.exam.entity.Test;
import com.exam.exception.QuestionBankNotFoundException;
import com.exam.exception.TestAlreadyExistException;
import com.exam.exception.TestNotFoundException;
import com.exam.repository.QuestionBankRepository;
import com.exam.repository.TestRepository;
import com.exam.service.TestService;

@Service
public class TestServiceimpl implements TestService {
	@Autowired
	private TestRepository testRepository;
	@Autowired
	private QuestionBankRepository questionBankRepository;

	@Override
	public String testCreate(Test test) throws QuestionBankNotFoundException, TestAlreadyExistException {
		Test existingTest = testRepository.findByQuestionBank(test.getQuestionBank());
		if (existingTest != null) {
			throw new TestAlreadyExistException("A test already exists for this question bank.");
		}
		Test existingTestName = testRepository.findByTestName(test.getTestName());
		if (existingTestName != null) {
			throw new TestAlreadyExistException("A test with this name already exists.");
		}
		QuestionBank questionBank = questionBankRepository.findById(test.getQuestionBank().getQbid())
				.orElseThrow(() -> new QuestionBankNotFoundException("Question bank not found."));
		test.setQuestionBank(questionBank);
		testRepository.save(test);

		return "Test created successfully.";
	}

	@Override
	public List<TestDto> testList() {

		List<Test> testInfo = testRepository.findAll();
		return testInfo.stream().map(tests -> {
			TestDto dto = new TestDto();
			dto.setTest_Name(tests.getTestName());
			dto.setStatus(tests.getStatus());
			dto.setTest_id(tests.getId());
			dto.setTest_date_time(tests.getTestDate());
			dto.setTest_time(tests.getTestTime());
			dto.setTotal_score(tests.getTotalScore());
			dto.setTotal_time(tests.getTotalTime());
			dto.setQuestionbankid(tests.getQuestionBank().getQbid());
			return dto;
		}).toList();
	}

	@Override
	public String updateTest(Test test)
			throws TestNotFoundException, QuestionBankNotFoundException {

		Test temp = testRepository.findById(test.getId())
				.orElseThrow(() -> new TestNotFoundException("Test Not Found"));

		QuestionBank bank = questionBankRepository.findById(test.getQuestionBank().getQbid())
				.orElseThrow(() -> new QuestionBankNotFoundException("Question Bank  Not Found"));
		if(bank!=null) {
			Test existingTest = testRepository.findByQuestionBank(test.getQuestionBank());
			temp.setQuestionBank(test.getQuestionBank());
			if (existingTest != null) {
				throw new TestNotFoundException("A test already exists for this question bank.");
			}
			if (Objects.nonNull(test.getTestTime())) {
				temp.setTestTime((test.getTestTime()));
			}
			if (Objects.nonNull(test.getTestDate())) {
				temp.setTestDate(test.getTestDate());
			}
			if (Objects.nonNull(test.getTestName()) && !"".equalsIgnoreCase(test.getTestName())) {
				temp.setTestName(test.getTestName());
			}
			if (Objects.nonNull(test.getStatus())) {
				temp.setStatus(test.getStatus());
			}
			if (Objects.nonNull(test.getTotalScore())) {
				temp.setTotalScore(test.getTotalScore());
			}
			if (Objects.nonNull(test.getTotalTime())) {
				temp.setTotalTime(test.getTotalTime());
			}

			testRepository.save(temp);
			return "Test updated Successfully";
		}else {
			return "Test updation error occured";
		}
		
	}

	@Override
	public String deleteTest(Long testId) throws TestNotFoundException {
		if (testRepository.findById(testId).isPresent()) {
			testRepository.deleteById(testId);
			return "Test deleted successfully";
		}else {
			throw new TestNotFoundException("Test Id is not valid, please give a valid test Id");
		}
		
	}

	@Override
	public List<String> availableTest() {

		return testRepository.findActiveTestName();
	}
}
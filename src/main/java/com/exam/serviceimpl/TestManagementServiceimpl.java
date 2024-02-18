package com.exam.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import java.util.Optional;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exam.dto.TestResultDto;

import com.exam.dto.QuestionDto;
import com.exam.dto.SingleUserReturn;
import com.exam.entity.Question;
import com.exam.entity.Result;
import com.exam.entity.Result.Response;
import com.exam.entity.Test;
import com.exam.entity.TestManagement;
import com.exam.entity.TestManagement.Status;
import com.exam.entity.User;
import com.exam.exception.TestNotFoundException;
import com.exam.exception.TestNotStartedException;
import com.exam.exception.UserNotFoundException;
import com.exam.exception.InvalidAnsException;
import com.exam.exception.QuestionNotFoundException;
import com.exam.repository.QuestionRepository;
import com.exam.repository.ResultRepository;
import com.exam.repository.TestManagementRepository;
import com.exam.repository.TestRepository;
import com.exam.repository.UsersRepository;
import com.exam.service.TestManagementService;

@Service
public class TestManagementServiceimpl implements TestManagementService {

	@Autowired
	TestManagementRepository testManagementRepository;

	@Autowired
	TestRepository testRepository;

	@Autowired
	UsersRepository userRepository;

	@Autowired
	QuestionRepository questionrepository;

	@Autowired
	ResultRepository resultRepository;

	@Override
	public String enrollTest(Long userid, Long testid) throws UserNotFoundException{
		Optional<User> user = userRepository.findById(userid);
		Optional<Test> test = testRepository.findById(testid);

		if (!user.isPresent() || !test.isPresent()) {

			throw new UserNotFoundException("User or Test not found");
		}
		TestManagement existingRecord = testManagementRepository.findByUserIdAndTestId(userid, testid);
		if (existingRecord != null) {
			return "Already Enrolled";
		} else {
			TestManagement testManagement = new TestManagement();
			testManagement.setUser(user.get());
			testManagement.setTest(test.get());
			testManagement.setStatus(Status.ENROLLED);

			Long questionBankId = test.get().getQuestionBank().getQbid();
			List<Question> questionList = questionrepository.findAllByQuestionbankid(questionBankId);
			questionList.forEach(e -> {
				Result result = new Result();
				result.setQuestion(e);
				result.setResponse(Response.UNATTEMPTED);
				result.setTest(test.get());
				result.setUser(user.get());
				resultRepository.save(result);
			});

			testManagementRepository.save(testManagement);
			return "Enrolled successfully";
		}
	}

	@Override
	public List<String> enrolledTest(Long userId) throws UserNotFoundException {
		if (!testManagementRepository.findById(userId).isPresent()) {
			throw new UserNotFoundException("Give a valid user Id");
		}

		return testManagementRepository.findAllByUser(userId).stream().map(e -> e.getTest().getId())
				.collect(Collectors.toList()).stream().map(f -> testRepository.findById(f).get().getTestName())
				.collect(Collectors.toList());
	}

	// Starting the test

	@Override
	public Question startTest(Long testid, long userid) throws TestNotFoundException {
		TestManagement testManagement = testManagementRepository.findByUserAndTest(userid, testid);
		if (testManagement != null) {
			// checking if the student is enrolled for the test
			if (testManagement.getStatus() == Status.ENROLLED) {
				testManagement.setStarttime(LocalTime.now());
				testManagement.setStatus(Status.STARTED);
				testManagementRepository.save(testManagement);
//				getting list of unattempted question id
				List<Result> unattemtedQuestionId = resultRepository.findByTestAndUser(userid, testid).stream()
						.filter(result -> result.getQuestion() != null)
						.filter(result -> result.getResponse() == Response.UNATTEMPTED).collect(Collectors.toList());
				if (unattemtedQuestionId.isEmpty()) {
					throw new TestNotFoundException("No more question Found");
				} else {
//					Generating random question id
					Random random = new Random();
					List<Long> unattemtedQuestionIds = unattemtedQuestionId.stream().map(e -> e.getQuestion().getId())
							.collect(Collectors.toList());
					Long randomQuestionId = unattemtedQuestionIds.get(random.nextInt(unattemtedQuestionId.size()));
//					Getting the question according to the random question id
					Question firstQuestion = questionrepository.findById(randomQuestionId).get();
					firstQuestion.setCorrectans(null);
					return firstQuestion;
				}
			} else {
				throw new TestNotFoundException("Test already Attempted");
			}
		} else {
			throw new TestNotFoundException("Please enroll first");
		}
	}

	@Override
	public String submittest(Long testid, long userid) throws TestNotStartedException, TestNotFoundException {
		TestManagement testManagement = testManagementRepository.findByUserAndTest(userid, testid);
		if (testManagement != null) {
			if (testManagement.getStatus() == Status.STARTED) {
				long count = resultRepository.findByTestAndUser(userid, testid).stream()
						.filter(result -> result.getResponse() == Response.CORRECT).count();
				testManagement.setObtainedScore((int) count);
				testManagement.setEndtime(LocalTime.now());
				testManagement.setTotalTimeTaken(LocalTime.parse("00:00:00")
						.plus(Duration.between(testManagement.getStarttime(), LocalTime.now())));
				testManagement.setStatus(Status.SUBMITTED);
				testManagementRepository.save(testManagement);
				return "Test Submitted! Please return to dashboard to check result.";
			} else {
				throw new TestNotStartedException("Test not started yet!");
			}
		} else {
			throw new TestNotFoundException("Test not found or user not enrolled in the test yet.");
		}
	}

	@Override
	public List<String> enrolledStudent(Long testId)throws TestNotFoundException {
		if (!testRepository.findById(testId).isPresent()) {
			throw new TestNotFoundException("Test Id is not valid");
		}
		
		return testManagementRepository.findAllByTest(testId).stream().map(e -> e.getUser().getId())
				.collect(Collectors.toList()).stream().map(g -> userRepository.findById(g).get().getName())
				.collect(Collectors.toList());
	}

	@Override
	public QuestionDto getNextQuestion(Long questionId, Long userId, Long testId, String result)
			throws TestNotFoundException, InvalidAnsException {
		Result res = resultRepository.findByTestAndUserAndQuestion(userId, testId, questionId);
		if (res != null && result != null) {
			if (result.equalsIgnoreCase("option1") || result.equalsIgnoreCase("option2")
					|| result.equalsIgnoreCase("option3") || result.equalsIgnoreCase("option4")) {
				res.setResult(result);
				if (questionrepository.findById(questionId).get().getCorrectans().toString().equals(result)) {
					res.setResponse(Response.CORRECT);
				} else {
					res.setResponse(Response.INCORRECT);
				}
				resultRepository.save(res);
			} else {
				throw new InvalidAnsException("Select an valid answer");
			}

		} else {
			throw new TestNotFoundException("Test is not attempting");
		}

//		getting list of unattempted question id
		List<Result> unattemtedQuestionId = resultRepository.findByTestAndUser(userId, testId).stream()
				.filter(resw -> resw.getQuestion() != null).filter(rest -> rest.getResponse() == Response.UNATTEMPTED)
				.collect(Collectors.toList());
		if (unattemtedQuestionId.isEmpty()) {
			throw new TestNotFoundException("No more question Found");
		} else {
//			Generating random question id
			Random random = new Random();
			List<Long> unattemtedQuestionIds = unattemtedQuestionId.stream().map(e -> e.getQuestion().getId())
					.collect(Collectors.toList());
			Long randomQuestionId = unattemtedQuestionIds.get(random.nextInt(unattemtedQuestionId.size()));
//			Getting the question according to the random question id
			Question firstQuestion = questionrepository.findById(randomQuestionId).get();
			QuestionDto questionDto = new QuestionDto();
			questionDto.setStatement(firstQuestion.getStatement());

			questionDto.setOption1(firstQuestion.getOption1());
			questionDto.setOption2(firstQuestion.getOption2());
			questionDto.setOption3(firstQuestion.getOption3());
			questionDto.setOption4(firstQuestion.getOption4());
			questionDto.setId(randomQuestionId);
			return questionDto;
		}

	}

	public String updateResponse(Long userid, Long testId, Long questionId, String resultReq)
			throws TestNotStartedException, TestNotFoundException, QuestionNotFoundException {
		Result result = resultRepository.findByTestAndUserAndQuestion(userid, testId, questionId);
		TestManagement testManagement = testManagementRepository.findByUserAndTest(userid, testId);
		if (testRepository.findById(testId) != null) {
			if (testManagement != null) {
				if (result == null) {
					throw new QuestionNotFoundException("Question Not found.");
				} else {
					if (resultReq == null) {
						result.setResult(null);
						result.setResponse(Response.UNATTEMPTED);
						resultRepository.save(result);
						return "Response cleared";
					} else {
						if (questionrepository.findById(questionId).get().getCorrectans().toString().equals(resultReq)) {
							result.setResponse(Response.CORRECT);
						} else {
							result.setResponse(Response.INCORRECT);
						}
						result.setResult(resultReq);
						resultRepository.save(result);
						return "Response Updated";
					}
				}
			} else {
				throw new TestNotFoundException("Enroll first. How you attempting the test? Call Developer team");
			}
		} else {
			throw new TestNotFoundException("Test not found. Team 5 what we are doing?");
		}
	}

	@Override
	public List<TestResultDto> studentReport(Long userid, Long testId) throws TestNotFoundException {
		List<TestManagement> testManagement = testManagementRepository.findByUser(userid);
		Test test = testRepository.findById(testId).orElseThrow(() -> new TestNotFoundException("Test not found"));
		if (testManagement.isEmpty()) {
			throw new TestNotFoundException("User has not attempted the test");
		} else {
			List<TestResultDto> response = new ArrayList<>();
			testManagement.forEach(e -> {
				TestResultDto testResultDto = new TestResultDto();
				testResultDto.setObtainedScore(e.getObtainedScore());
				testResultDto.setTimeTaken(e.getTotalTimeTaken());
				testResultDto.setTestDate(test.getTestDate());
				testResultDto.setTestName(test.getTestName());
				testResultDto.setTestTime(test.getTestTime());
				testResultDto.setTotalScore(test.getTotalScore());
				response.add(testResultDto);
			});
			return response;
		}
	}

	@Override
	public QuestionDto previousQuestion(Long userId, Long questionId) throws QuestionNotFoundException {
		// getting list of all previous question
		ArrayList<Long> previousQuestionIds = (ArrayList<Long>) resultRepository.findLatestResultByUser(userId).stream()
				.map(e -> e.getQuestion().getId()).collect(Collectors.toList());
		int index = previousQuestionIds.indexOf(questionId);
		Long nextValue;
//		finding the question attempted before the given question id
		if (index >= 0 && index < previousQuestionIds.size() - 1) {
			nextValue = previousQuestionIds.get(index + 1);
		} else {
			throw new QuestionNotFoundException("No more previous question");
		}
//		finding the question in question table
		Question question = questionrepository.findById(nextValue).get();
		if (question != null) {
			QuestionDto questionDto = new QuestionDto();
			questionDto.setId(nextValue);
			questionDto.setStatement(question.getStatement());
			questionDto.setOption1(question.getOption1());
			questionDto.setOption2(question.getOption2());
			questionDto.setOption3(question.getOption4());
			questionDto.setOption3(question.getOption4());
			return questionDto;
		} else {
			throw new QuestionNotFoundException("No more previous question");
		}

	}

	@Override
	public List<SingleUserReturn> attemptedStudent(Long id) throws TestNotFoundException {
	    List<TestManagement> allAttempted = testManagementRepository.findListByTestrAndStatus1(id);
	    if(allAttempted==null) {
	    	throw new TestNotFoundException("Test Not found");
	    }else {
	    	  List<SingleUserReturn> attemptedUsers = allAttempted.stream()
	    		        .map(attempt -> new SingleUserReturn(
	    		            attempt.getUser().getId(),
	    		            attempt.getUser().getName(),
	    		            attempt.getUser().getPhone(),
	    		            attempt.getUser().getRoles(),
	    		            attempt.getUser().getAddress()
	    		        ))
	    		        .collect(Collectors.toList());
	    	  return attemptedUsers;
	    }
	    
	}


}

package com.exam.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.exam.entity.Question;
import com.exam.entity.QuestionBank;
import com.exam.exception.QuestionBankNotFoundException;
import com.exam.exception.QuestionNotFoundException;
import com.exam.exception.QuestionStatementNotUniqueException;
import com.exam.repository.QuestionBankRepository;
import com.exam.repository.QuestionRepository;
import com.exam.service.QuestionService;

import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

import org.springframework.validation.Errors;

@Service
public class QuestionServiceimpl implements QuestionService {

	@Autowired
	QuestionRepository questionRepository;
	@Autowired
	QuestionBankRepository questionBankRepository;
	@Override
	public String add_question(Question question)
			throws MethodArgumentNotValidException, QuestionBankNotFoundException {

		Long questionBankId = question.getQuestionbankid();
		Optional<QuestionBank> qBank = questionBankRepository.findById(questionBankId);
		if (question != null && questionBankId != null && qBank.isPresent()) {
			try {
				questionRepository.save(question);
			} catch (DataIntegrityViolationException e) {
				Errors errors = new BindException(question, "question");
				errors.rejectValue("statement", "duplicate", "Statement should be unique");
				throw new MethodArgumentNotValidException(null, (BindingResult) errors);
			}


			Question nextQuestion = questionRepository.findByStatement(question.getStatement());

			if (qBank.isPresent()) {
				List<Long> questionIds = qBank.get().getQuestionid();
				if (questionIds == null) {
					questionIds = new ArrayList<>();
				}
				questionIds.add(nextQuestion.getId());
				qBank.get().setQuestionid(questionIds);
				questionBankRepository.save(qBank.get());
			}


			return "Success";
		} else {
			throw new QuestionBankNotFoundException("Question bank id is not valid");
		}
	}

	@Override
	public String edit_question(Question question) throws QuestionNotFoundException, QuestionBankNotFoundException, QuestionStatementNotUniqueException {
	   Optional<Question> existingOptionalQuestion = questionRepository.findById(question.getId());
	    if((!existingOptionalQuestion.isEmpty())|| (!existingOptionalQuestion.isPresent())) {
	    	throw new QuestionNotFoundException("Question Id is not valid");
	    }
	    Question existingQuestion = existingOptionalQuestion.get();
	    if((!questionBankRepository.findById(question.getQuestionbankid()).isEmpty()) || (!questionBankRepository.findById(question.getQuestionbankid()).isPresent())) {
	    	throw new QuestionBankNotFoundException("Question Bank Id is not valid");
	    }
	    if (Objects.nonNull(question.getStatement()) && !"".equalsIgnoreCase(question.getStatement())) {
	        Question duplicateQuestion = questionRepository.findByStatementAndIdNot(question.getStatement(), question.getId());
	        if (duplicateQuestion != null) {
	            throw new QuestionStatementNotUniqueException("Question statement already exists");
	        }
	        existingQuestion.setStatement(question.getStatement());
	    }
	    if (Objects.nonNull(question.getOption1()) && !"".equalsIgnoreCase(question.getOption1())) {
	        existingQuestion.setOption1(question.getOption1());
	    }
	    if (Objects.nonNull(question.getOption2()) && !"".equalsIgnoreCase(question.getOption2())) {
	        existingQuestion.setOption2(question.getOption2());
	    }
	    if (Objects.nonNull(question.getOption3()) && !"".equalsIgnoreCase(question.getOption3())) {
	        existingQuestion.setOption3(question.getOption3());
	    }
	    if (Objects.nonNull(question.getOption4()) && !"".equalsIgnoreCase(question.getOption4())) {
	        existingQuestion.setOption4(question.getOption4());
	    }
	    if (Objects.nonNull(question.getCorrectans())) {
	        existingQuestion.setCorrectans(question.getCorrectans());
	    }
	    questionRepository.save(existingQuestion);
	    return "Question updated successfully";

	}


	@Override
	public String delete_question(Long id) throws QuestionNotFoundException {
	    Optional<Question> questionOptional = questionRepository.findById(id);
	    if (!questionOptional.isPresent()) {
	        throw new QuestionNotFoundException("Question with ID " + id + " not found");
	    }
	    questionRepository.deleteById(id);
	    return "Question deleted successfully";
	}


	@Override
	public List<Question> view_question(Long questionbankid) throws QuestionBankNotFoundException {
	    Optional<QuestionBank> questionBank = questionBankRepository.findById(questionbankid);
	    if (!questionBank.isPresent()) {
	        throw new QuestionBankNotFoundException("QuestionBank Id is not valid");
	    }
	    return questionRepository.findAllByQuestionbankid(questionbankid);
	}


}

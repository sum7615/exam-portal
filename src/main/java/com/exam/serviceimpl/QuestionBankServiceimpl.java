package com.exam.serviceimpl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.exam.dto.QuestionBankDto;
import com.exam.entity.QuestionBank;
import com.exam.exception.QuestionBankNotFoundException;
import com.exam.repository.QuestionBankRepository;
import com.exam.service.QuestionBankService;

@Service
public class QuestionBankServiceimpl implements QuestionBankService {

	public QuestionBank questionBankuser = new QuestionBank();

	@Autowired
	public QuestionBankRepository questionBankRepository;

	@Override
	public String add_question_bank(QuestionBank questionBank) throws QuestionBankNotFoundException {

		String regexp = "^[0-9]$";
		QuestionBank existingQuestionBankName = questionBankRepository.findByName(questionBank.getName());
		if ((questionBank.getName().isEmpty()) || (Pattern.matches(regexp, questionBank.getName()))||(existingQuestionBankName != null)) {
			throw new QuestionBankNotFoundException("QuestionBank name is not valid");
		} else {
			questionBankRepository.save(questionBank);
			return "Question Bank added successfully";
		}
	}

	@Override
	public ResponseEntity<String> update_question_bank(QuestionBank questionBank) throws NoSuchElementException {
	    QuestionBank questionBankuser = questionBankRepository.findById(questionBank.getQbid()).orElseThrow(() -> new NoSuchElementException("QuestionBank Id is not found"));
	    questionBankuser.setName(questionBank.getName());
	    questionBankRepository.save(questionBankuser);
	    return ResponseEntity.ok("Question bank name updated");
	}


	@Override
	public ResponseEntity<String> delete_question_bank(Long id) throws QuestionBankNotFoundException {
		QuestionBank questionBankuser = questionBankRepository.findById(id).orElseThrow(() -> new NoSuchElementException("QuestionBank Id is not found"));
		questionBankRepository.deleteById(id);
		return ResponseEntity.ok("Question Bank deleted successfully");

	}

	@Override
	public List<QuestionBankDto> list_question_bank(){
		List<QuestionBank> questionBanks = questionBankRepository.findAll();
		return questionBanks.stream().map(qb -> {
			QuestionBankDto dto = new QuestionBankDto();
			dto.setQbid(qb.getQbid());
			dto.setQuestion_bank_name(qb.getName());
			dto.setTotal_question(qb.getQuestionid() != null ? qb.getQuestionid().size() : 0);
			return dto;
		}).collect(Collectors.toList());

	}

}

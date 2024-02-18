package com.exam.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.exam.dto.QuestionBankDto;
import com.exam.entity.QuestionBank;
import com.exam.exception.QuestionBankNotFoundException;

@Service
public interface QuestionBankService {

	public String add_question_bank(QuestionBank questionBank) throws QuestionBankNotFoundException;

	public ResponseEntity<String> update_question_bank(QuestionBank questionBank) throws NoSuchElementException;


	public ResponseEntity<String> delete_question_bank(Long id) throws QuestionBankNotFoundException;

	public List<QuestionBankDto> list_question_bank()throws QuestionBankNotFoundException;

}

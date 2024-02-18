package com.exam.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.exam.entity.Question;
import com.exam.exception.QuestionBankNotFoundException;
import com.exam.exception.QuestionNotFoundException;
import com.exam.exception.QuestionStatementNotUniqueException;

@Service
public interface QuestionService {
		
	public String add_question(Question question)throws  MethodArgumentNotValidException,QuestionBankNotFoundException;
	//public String edit_question(Question question) throws QuestionNotFoundException, QuestionBankNotFoundException, QuestionStatementNotUniqueException;
	public String delete_question(Long id)throws QuestionNotFoundException;
	public List<Question> view_question(Long questionbankid) throws QuestionBankNotFoundException;
	public String edit_question(Question question)throws QuestionStatementNotUniqueException,QuestionBankNotFoundException, QuestionNotFoundException;
}

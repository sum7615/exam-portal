package com.exam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exam.entity.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

	List<Question> findAllByQuestionbankid(long questionbankid);

	Question findByStatement(String statement);

	Question findByStatementAndIdNot(String statement, long id);

	List<Question> findAllByQuestionbankid(Long questionbankid);


	//Question findByStatementAndQuestionBankAndIdNot(String statement,Long questionBankId, long id);



}

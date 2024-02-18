package com.exam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.exam.entity.QuestionBank;
import com.exam.entity.Test;



@Repository
public interface TestRepository extends JpaRepository<Test,Long> {
	
@Query("SELECT t.testName FROM Test t WHERE t.status = 'Active'")
List<String> findActiveTestName();


@Query("SELECT t.testName FROM Test t WHERE questionBank.id = id")
Test findByQuestionBankId(@Param("id") Long id );


Test findByQuestionBank(QuestionBank questionBank);

Test findByTestName(String testName);




}

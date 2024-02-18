package com.exam.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.exam.entity.Result;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long> {
	@Query("select t from Result t where t.user.id = :userId and t.test.id = :testid")
	List<Result> findByTestAndUser(@Param("userId") Long userId, @Param("testid") Long testid);

	@Query("select t from Result t where t.user.id = :userId and t.test.id = :testid and t.question.id =:questionId")
	Result findByTestAndUserAndQuestion(@Param("userId") Long userId, @Param("testid") Long testid,
			@Param("questionId") Long questionId);

	@Query("select t from Result t where t.question.id = :questionId")
	List<Result> findByQuestionId(@Param("questionId") Long questionId);

	@Query("SELECT e FROM Result e WHERE e.user.id = :userId AND e.response != 'UNATTEMPTED' ORDER BY e.lastUpdated DESC")
	ArrayList<Result> findLatestResultByUser(@Param("userId") Long userId);

}

package com.exam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.exam.entity.TestManagement;

@Repository
public interface TestManagementRepository extends JpaRepository<TestManagement, Long> {

	@Query("select t from TestManagement t where t.user.id = :userId and t.status = 'ENROLLED'")
	List<TestManagement> findAllByUser(@Param("userId") Long userId);

	TestManagement findByUserIdAndTestId(long userid, long testid);

	@Query("select t from TestManagement t where t.test.id = :testId and t.status = 'ENROLLED'")
	List<TestManagement> findAllByTest(@Param("testId") Long testId);
	
	@Query("select t from TestManagement t where t.test.id = :testId")
	List<TestManagement> findAllByTestId(@Param("testId") Long testId);
	
	
	
	@Query("select t from TestManagement t where t.user.id = :userId and t.test.id = :testid")
	TestManagement findByUserAndTest(@Param("userId") Long userId, @Param("testid") Long testid);

	@Query("select t from TestManagement t where t.user.id = :userId")
	List<TestManagement> findByUser(@Param("userId") Long userId);

	@Query("select COUNT(*) from TestManagement t where t.user.id = :testId and t.status = :status")
	int findByTestrAndStatus(@Param("testId") Long testId,@Param("status")TestManagement.Status status);	
	
	@Query("select t from TestManagement t where t.test.id = :testId and t.status = 'SUBMITTED'")
	List<TestManagement> findListByTestrAndStatus1(@Param("testId") Long testId);		
	
	
}

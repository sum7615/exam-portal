package com.exam.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.exam.entity.User;
public interface UsersRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String str);
	
	@Query("from User u where Upper(u.username) = Upper(:userName)")
	Optional<User> getByUserName(@Param("userName") String userName);
	
}

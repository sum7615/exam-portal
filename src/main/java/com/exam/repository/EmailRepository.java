package com.exam.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.entity.Email;

public interface EmailRepository extends JpaRepository<Email, Long> {
    Optional<Email> findByEmailAddress(String emailAddress);
}

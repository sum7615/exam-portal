package com.exam.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.entity.Telephone;

public interface TelephoneRepository extends JpaRepository<Telephone, Long> {
    Optional<Telephone> findByPhoneNumber(String phoneNumber);
}

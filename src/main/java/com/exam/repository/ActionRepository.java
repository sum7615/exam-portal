package com.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exam.entity.Action;

public interface ActionRepository extends JpaRepository<Action, Long> {
}

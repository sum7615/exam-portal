package com.exam.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.exam.entity.AppProperties;

public interface AppPropertiesRepo extends JpaRepository<AppProperties, Long> {

	 List<AppProperties> findByPage(@Param("page") String page);
}

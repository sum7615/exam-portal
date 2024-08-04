package com.exam.entity;

import org.hibernate.annotations.NamedQuery;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data


	@NamedQuery(
			name = "AppProperties.getByPage",
			query = "FROM AppProperties WHERE LOWER(page)= LOWER(:page)"
			)
public class AppProperties {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long propertyId;
	public String propertyName;
	public String propertyValue;
	public String page;
	
}

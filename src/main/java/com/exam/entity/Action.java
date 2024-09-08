package com.exam.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "action")
@SequenceGenerator(name = "action_seq", sequenceName = "action_seq", allocationSize = 1)
@Data
@Getter
@Setter
public class Action {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "action_seq")
	private Long id;

	@Column(name = "action_name", nullable = false)
	private String actionName;

	@Column(name = "description")
	private String description;

}
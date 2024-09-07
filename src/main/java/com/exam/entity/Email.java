package com.exam.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "email")
@Getter
@Setter
@Data
@SequenceGenerator(name = "email_seq", sequenceName = "email_seq", allocationSize = 1)
public class Email {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "email_seq")

	private Long id;

	@Column(name = "email_address", nullable = false)
	private String emailAddress;

	@Column(name = "email_type")
	private String emailType;

	@ManyToMany(mappedBy = "emails")
	private Set<User> users = new HashSet<>();
}

package com.exam.entity;

import java.util.Date;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Result {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;
	private String result;

	@Enumerated(EnumType.STRING)
	@Column(name = "response")
	private Response response;

	public enum Response {
		CORRECT, INCORRECT, UNATTEMPTED

	}

	@OneToOne
	@JoinColumn(name = "testid", referencedColumnName = "id")
	private Test test;

	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "questionid", referencedColumnName = "id")
	private Question question;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "userid", referencedColumnName = "id")
	private User user;
	@UpdateTimestamp
	private Date lastUpdated;

}

package com.exam.entity;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TestManagement {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private long id;
	@Column(name = "obtainscore")
	private int obtainedScore;
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private Status status;

	public enum Status {
		ENROLLED, MISSED, STARTED, ATTEMPTED, SUBMITTED
	}
	@JsonFormat(pattern = "hh:mm:ss")
	@Column(name = "starttime")
	private LocalTime starttime;
	
	@JsonFormat(pattern = "hh:mm:ss")
	@Column(name = "endtime")
	private LocalTime endtime;
	
	@JsonFormat(pattern = "hh:mm:ss")
	@Column(name = "totaltimetaken")
	private LocalTime totalTimeTaken;
	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "userid", referencedColumnName = "id")
	public User user;
	@OneToOne

	@JoinColumn(name = "testid", referencedColumnName = "id")
	private Test test;
}

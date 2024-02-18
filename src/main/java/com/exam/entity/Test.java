package com.exam.entity;


import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

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
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Test {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name="id")
	private long id;
	
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name="testdate")
	@NotNull(message = "TestDate is not valid")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
	@FutureOrPresent(message = "The date must be in present or future")
	private LocalDate testDate;
	
	
	@JsonFormat(pattern = "hh:mm:ss")
	@Column(name="testtime")
	@NotNull(message = "TestTime is not valid")
	private LocalTime testTime;
	
	
	@JsonFormat(pattern = "hh:mm:ss")
	@Column(name="totaltime")
	@NotNull(message = "TotalTime is not valid")
	//@FutureOrPresent(message= "The time must be in present or future")
	private LocalTime totalTime;
	
	
	
	@Column(name="totalscore")
	@Min(value = 1, message = "Total score should be greater than 0")
	@NotNull(message = "Testscore field is empty")
	private int totalScore;
	
	
	@Column(name="testname")
	@NotBlank(message = "Testname should not be null")
	private String testName;
	
	
	@Enumerated(EnumType.STRING)
	@Column(name="status")
	private Status status;
	public enum Status{
		Active,
		Inactive
	}
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "questionbankid",
	              referencedColumnName = "qbid"
	)
	private QuestionBank questionBank;
	
	
	}

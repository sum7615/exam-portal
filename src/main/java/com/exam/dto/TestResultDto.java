package com.exam.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestResultDto {
	private String testName;
	private LocalDate testDate;
	private LocalTime testTime;
	private LocalTime timeTaken;
	private int totalScore;
	private int obtainedScore;
}

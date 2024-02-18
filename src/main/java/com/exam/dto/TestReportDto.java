package com.exam.dto;

import java.time.LocalTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestReportDto {

	private String testName;
	private int enrolled;
	private int atttempted;
	private int totalScore;
	private LocalTime averageTimeTaken;
	private List<StudentResultDto> students;
}

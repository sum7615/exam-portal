package com.exam.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestReportStudentObjectDto {
	private String test_name;
	private int total_score;
	private int obtained_score;
	private LocalDate test_date;
	private LocalTime time_taken;
	private List<TestReportStudentDto> questions;

}

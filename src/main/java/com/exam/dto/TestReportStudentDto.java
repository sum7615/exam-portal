package com.exam.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestReportStudentDto {
	private String statement;
	private String response_by_student;
	private String correct_ans;
}

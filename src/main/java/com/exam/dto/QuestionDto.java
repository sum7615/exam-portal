package com.exam.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuestionDto {

	private String statement;
	private String option1;
	private String option2;
	private String option3;
	private String option4;
	private Long id;
}

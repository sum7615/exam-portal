package com.exam.dto;


import java.time.LocalTime;

import com.exam.entity.TestManagement.Status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TestManagementDto {


	private long previous_question_id;
	private String statement;
	private String option1;
	private String option2;
	private String option3;
	private String option4;
	private long id;
	private int obtainedScore;
	private Status status;
	private LocalTime totalTimeTaken;
	public Long userid;
	private Long testid;
	private Long questionid;
	private String result;

}

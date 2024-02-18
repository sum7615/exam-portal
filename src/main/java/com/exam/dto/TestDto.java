package com.exam.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.exam.entity.Test.Status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TestDto {
	private String test_Name;
	private Long test_id;
	private Long questionbankid;	
	private LocalDate test_date_time;
	private LocalTime test_time;
	private LocalTime total_time;
	private Status status;
	private int total_score;
}
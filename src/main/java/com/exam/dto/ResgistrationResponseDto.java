package com.exam.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResgistrationResponseDto {
	private Long id;
	private String name;
	private String username;
	private String phone;
	private String address;
	private String roles;
}

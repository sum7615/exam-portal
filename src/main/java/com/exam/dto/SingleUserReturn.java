package com.exam.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SingleUserReturn {
	private long id;
	private String name;
	private String Address;
	private String username;
	private String phone;
	

}

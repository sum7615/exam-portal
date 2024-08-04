package com.exam.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class LookUpDataDto {
	
	public String propertyName;
	public String propertyValue;
	public String page;
}

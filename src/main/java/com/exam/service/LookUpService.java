package com.exam.service;

import java.util.List;

import com.exam.dto.LookUpDataDto;

public interface LookUpService {

	List<LookUpDataDto> getPageProp(String page);

}

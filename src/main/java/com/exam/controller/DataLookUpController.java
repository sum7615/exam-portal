package com.exam.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exam.dto.LookUpDataDto;
import com.exam.exception.InvalidParamException;
import com.exam.service.LookUpService;

@RestController
@RequestMapping("/lookup")
public class DataLookUpController {
	
	@Autowired
	LookUpService lookUpService;
	
	@GetMapping("/page/{page}")
	public List<LookUpDataDto> getPageProp(@PathVariable("page") String page) {
		if(page!=null && !page.trim().isEmpty() ) {
			return lookUpService.getPageProp(page);
		}else {
			throw new InvalidParamException("Not valid parameter");
		}
	}
	
}

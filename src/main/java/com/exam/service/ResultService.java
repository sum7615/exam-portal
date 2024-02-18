package com.exam.service;


import org.springframework.stereotype.Service;

import com.exam.dto.TestReportDto;
import com.exam.dto.TestReportStudentObjectDto;
import com.exam.exception.ReportGenerationException;
import com.exam.exception.TestNotFoundException;

@Service
public interface ResultService {
	
	TestReportDto testReport(Long testid) throws TestNotFoundException;
	TestReportStudentObjectDto testReportStudent(Long userId, Long testId)throws ReportGenerationException;

}

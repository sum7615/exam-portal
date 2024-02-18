package com.exam.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.exam.dto.QuestionBankDto;
import com.exam.entity.QuestionBank;
import com.exam.service.QuestionBankService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class) 
    class QuestionBankControllerTest{
	
    	 private MockMvc mockMvc;
    	    private ObjectMapper objectMapper = new ObjectMapper();
    	    
    	    @Mock
    	    private QuestionBankService questionBankService;
    	    
    	    @InjectMocks
    	    private QuestionBankController questionBankController;
    	    
    	    @BeforeEach
    	    public void setUp() {
    	        mockMvc = MockMvcBuilders.standaloneSetup(questionBankController).build();
    	    }
    	    
    	    @Test
    	    @DisplayName("QuestionBank Add Testing Successfully")
    	    public void testAddQuestionBankSuccess() throws  Exception{
    	    	QuestionBank qb = new QuestionBank();
    	    	qb.setName("Spring Boot");
    	    
    	    	String exceptedResponse = "Question Bank added successfully";
    	    	when(questionBankService.add_question_bank (any(QuestionBank.class))).thenReturn(exceptedResponse);
    	        
    	        mockMvc.perform(post("/question-bank")
    	                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(qb)))
    	                .andExpect(status().isOk())
    	                .andExpect(content().string(exceptedResponse));
    	    	
    	    }
    	    
    
    	    
    	    @Test
    		@DisplayName("Question Bank update Testing Successfully")
    		public void testUpdateQuestionBank()throws Exception{
    			QuestionBank qb = new QuestionBank();
    			qb.setQbid(1L);
    			qb.setName("Spring Question Bank");
    			
    			String expectedResponse ="Question bank name updated";
    			
    			ResponseEntity<String> responseEntity = ResponseEntity.ok(expectedResponse);

    			when(questionBankService.update_question_bank(any(QuestionBank.class)))
    			.thenReturn(responseEntity);
    			
    			
    			mockMvc.perform(put("/question-bank")
    			        .contentType(MediaType.APPLICATION_JSON)
    			        .content(objectMapper.writeValueAsString(qb)))
    			        .andExpect(status().isOk())
    			        .andExpect(content().string(expectedResponse));
    		}
    	   
    	    @Test
    	    @DisplayName("Question Bank Delete Testing Success")
    	    public void testDeleteQuestionBankSuccess() throws  Exception {
    	    	QuestionBankDto qb = new QuestionBankDto();
    	    	qb.setQbid(1L);
    	    	
    	    	String expectedResponse = "Question Bank Deleted";
    	    	ResponseEntity<String> responseEntity = ResponseEntity.ok(expectedResponse);

    	    	when(questionBankService.delete_question_bank(1L))
    	    	    .thenReturn(responseEntity);

    	    	mockMvc.perform(MockMvcRequestBuilders.delete("/delete-quesBank", 1L));

    	    	ResponseEntity<String> result = questionBankService.delete_question_bank(1L);
    	    	String actualResponse = result.getBody();
    	    	assertEquals(expectedResponse, actualResponse);

    	    	}

    	    
    	    @Test
    		@DisplayName("Question Bank list  Testing Successfully")
    		public void testListQuestionBankSuccess() throws Exception {
    			List<QuestionBankDto> expectedQuestionBankList = new ArrayList<>();
    			expectedQuestionBankList.add(new QuestionBankDto(1L, "Spring question Bank", 40L));
    			expectedQuestionBankList.add(new QuestionBankDto(2L, "AWS question Bank", 20L));

    			when(questionBankService.list_question_bank()).thenReturn(expectedQuestionBankList);
    			List<QuestionBankDto> actualQuestionBankList = questionBankService.list_question_bank();

    			assertEquals(expectedQuestionBankList, actualQuestionBankList);
    			verify(questionBankService).list_question_bank();

    			mockMvc.perform(get("/questionbanklist"))
    				.andExpect(status().isOk())
    				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
    				.andExpect(content().json(objectMapper.writeValueAsString(actualQuestionBankList)));
    		}
    		
  
    
}
    

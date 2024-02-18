package com.exam.controller;

import static org.junit.Assert.assertEquals;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.exam.dto.QuestionBankDto;
import com.exam.dto.QuestionDto;
import com.exam.entity.Question;
import com.exam.service.QuestionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.TestReporter;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.exam.dto.TestDto;
import com.exam.entity.Question;
import com.exam.entity.QuestionBank;
import com.exam.entity.Test.Status;
import com.exam.repository.TestRepository;
import com.exam.service.TestService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class TestControllerTest {

//	 private MockMvc mockMvc;
//	    private ObjectMapper objectMapper = new ObjectMapper();
//	    
//	    @Mock
//	    private TestService testService;
//	    
//	    @InjectMocks
//	    private TestController testController;
//	    
//	    @BeforeEach
//	    public void setUp() {
//	        mockMvc = MockMvcBuilders.standaloneSetup(testController).build();
//	        MockitoAnnotations.openMocks(this);
//	    }
//	    
//	    @Test
//	    @DisplayName("Create Test Testing Successfully")
//	    public void testTestCreateSuccess() throws Exception {
//			objectMapper.registerModule(new JavaTimeModule());
//	        // Prepare test request data
//	    	TestDto testDto = new TestDto();
//		    testDto.setTest_id(1L);
//		    testDto.setTest_Name("AWS test");
//		    testDto.setTest_date_time(LocalDate.parse("2023-12-11"));
//		    testDto.setTest_time(LocalTime.parse("03:10:00"));
//		    testDto.setStatus(Status.Active);
//		    testDto.setTotal_score(100);
//		    testDto.setTotal_time(LocalTime.parse("03:10:00"));
//		    testDto.setQuestionbankid(1L);
//
//	        // Prepare test response data
//	        String expectedResponse = "Test Created Successfully";
//	        when(testService.test_create(any(com.exam.entity.Test.class))).thenReturn(expectedResponse);
//
//	        // Perform the controller method call
//	        mockMvc.perform(MockMvcRequestBuilders.post("/test")
//	                .contentType(MediaType.APPLICATION_JSON)
//	                .content(objectMapper.writeValueAsString(testDto)))
//	            .andExpect(status().isOk())
//	            .andExpect(content().string(expectedResponse));
// 
//	    }
//	    
////	    @Test
////	    @DisplayName("Create Test Testing Failed")
////	    public void testTestCreateFail() throws Exception {
////			objectMapper.registerModule(new JavaTimeModule());
////	        // Prepare test request data
////	    	TestDto testDto = new TestDto();
////		    testDto.setTest_id(1L);
////		    testDto.setTest_Name("AWS test");
////		    testDto.setTest_date_time(LocalDate.parse("2023-12-11"));
////		    testDto.setTest_time(LocalTime.parse("03:10:00"));
////		    testDto.setStatus(Status.Active);
////		    testDto.setTotal_score(100);
////		    testDto.setTotal_time(LocalTime.parse("03:10:00"));
////		    testDto.setQuestionbankid(1L);
////
////	        // Prepare test response data
////	        String expectedResponse = "Test Created Successfully";
////	        when(testService.test_create(any(com.exam.entity.Test.class))).thenReturn(expectedResponse);
////
////	        // Perform the controller method call
////	        mockMvc.perform(MockMvcRequestBuilders.post("/test")
////	                .contentType(MediaType.APPLICATION_JSON)
////	                .content(objectMapper.writeValueAsString(testDto)))
////	            .andExpect(status().isOk())
////	            .andExpect(content().string("QuestionBank not found"));
//// 
////	    }
//	    
//	    @Test
//	    @DisplayName("Update Test Testing Successfully")
//	    public void testUpdateTestSuccess() throws Exception {
//			objectMapper.registerModule(new JavaTimeModule());
//	        // Prepare test request data
//	    	TestDto testDto = new TestDto();
//		    testDto.setTest_id(1L);
//		    testDto.setTest_Name("AWS test");
//		    testDto.setTest_date_time(LocalDate.parse("2023-12-11"));
//		    testDto.setTest_time(LocalTime.parse("03:10:00"));
//		    testDto.setStatus(Status.Active);
//		    testDto.setTotal_score(100);
//		    testDto.setTotal_time(LocalTime.parse("03:10:00"));
//		    testDto.setQuestionbankid(1L);
//	    
//		    String expectedResponse = "Test Updated Successfully";
//	        when(testService.update_test(any(com.exam.entity.Test.class))).thenReturn(expectedResponse);
//
//	        // Perform the controller method call
//	        mockMvc.perform(MockMvcRequestBuilders.put("/test")
//	                .contentType(MediaType.APPLICATION_JSON)
//	                .content(objectMapper.writeValueAsString(testDto)))
//	            .andExpect(status().isOk())
//	            .andExpect(content().string(expectedResponse));   
//	}
//	    
////	    @Test
////	    @DisplayName("Update Test Testing Failed")
////	    public void testUpdateTestFail() throws Exception {
////			objectMapper.registerModule(new JavaTimeModule());
////	        // Prepare test request data
////	    	TestDto testDto = new TestDto();
////		    testDto.setTest_id(1L);
////		    testDto.setTest_Name("AWS test");
////		    testDto.setTest_date_time(LocalDate.parse("2023-12-11"));
////		    testDto.setTest_time(LocalTime.parse("03:10:00"));
////		    testDto.setStatus(Status.Active);
////		    testDto.setTotal_score(100);
////		    testDto.setTotal_time(LocalTime.parse("03:10:00"));
////		    testDto.setQuestionbankid(1L);
////	    
////		    String expectedResponse = "Test Updated Successfully";
////	        when(testService.update_test(any(com.exam.entity.Test.class), null)).thenReturn(expectedResponse);
////
////	        // Perform the controller method call
////	        mockMvc.perform(MockMvcRequestBuilders.put("/test")
////	                .contentType(MediaType.APPLICATION_JSON)
////	                .content(objectMapper.writeValueAsString(testDto)))
////	            .andExpect(status().isOk())
////	            .andExpect(content().string("QuestionBank not found"));
////     }
//
//	    @Test
//		 @DisplayName("Delete Test Testing Successfully")
//		    public void testDeleteTestSuccess() throws Exception {
//		        com.exam.entity.Test tests = new com.exam.entity.Test();
//		        tests.setId(1L);
//               
//		        String expectedResponse = "Test deleted successfully";
//		        when(testService.delete_test(1L)).thenReturn(expectedResponse);
//		        
//		       
//		        mockMvc.perform(MockMvcRequestBuilders.delete("/test", 1L));
//		        String result = testService.delete_test(1L);
//		        verify(testService, times(1)).delete_test(tests.getId());
//		        assertEquals("Test deleted successfully", result);
//		    }
////		 @Test
////		 @DisplayName("Delete Test Testing Failed")
////		 public void testDeleteTestFail() throws Exception {
////		     com.exam.entity.Test test = new com.exam.entity.Test();
////		     test.setId(1L);
////		     String expectedResponse = "Test deleted successfully";
////		        when(testService.delete_test(1L)).thenReturn(expectedResponse);
////		        
////		       
////		        mockMvc.perform(MockMvcRequestBuilders.delete("/test", 1L));
////		        String result = testService.delete_test(2L);
////		        assertEquals("No Test Found", result);
////		 }
//		 
//
//		 
//		 @Test
//		 @DisplayName("Test list Testing Successfully")
//		 public void test_test_list() throws Exception {
//		     ObjectMapper objectMapper = new ObjectMapper();
//		     objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
//		     objectMapper.registerModule(new JavaTimeModule());
//		     LocalDate ld = LocalDate.of(2022, 10, 20);
//		     LocalTime lt = LocalTime.of(12, 15, 30);
//		     LocalTime lt1 = LocalTime.of(10, 30, 40);
//
//		     TestDto test1 = new TestDto();
//		     test1.setTest_id(23L);
//		     test1.setTest_Name("AWS test");
//		     test1.setTest_date_time(ld);
//		     test1.setTest_time(lt);
//		     test1.setStatus(Status.Active);
//		     test1.setTotal_score(100);
//		     test1.setTotal_time(lt1);
//		     test1.setQuestionbankid(1L);
//
//		     TestDto test2 = new TestDto();
//		     test2.setTest_id(24L);
//		     test2.setTest_Name("Azure test");
//		     test2.setTest_date_time(ld);
//		     test2.setTest_time(lt);
//		     test2.setStatus(Status.Inactive);
//		     test2.setTotal_score(90);
//		     test2.setTotal_time(lt1);
//		     test2.setQuestionbankid(2L);
//
//		     List<TestDto> expectedTestList = new ArrayList<>();
//		     expectedTestList.add(test1);
//		     expectedTestList.add(test2);
//
//		     when(testService.test_list()).thenReturn(expectedTestList);
//
//		     MvcResult mvcResult = mockMvc.perform(get("/test-list"))
//		             .andExpect(status().isOk())
//		             .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//		             .andReturn();
//
//		     String expectedResponse = objectMapper.writeValueAsString(Collections.singletonMap("tests", expectedTestList));
//		     String actualResponse = mvcResult.getResponse().getContentAsString();
//
//		     JSONAssert.assertEquals(expectedResponse, actualResponse, false);
//
//		     verify(testService).test_list();
//		 }
//

		
	    
}   


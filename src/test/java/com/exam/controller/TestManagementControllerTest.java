package com.exam.controller;

import org.junit.jupiter.api.BeforeEach;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.exam.dto.QuestionDto;
import com.exam.dto.TestManagementDto;
import com.exam.entity.Question;
import com.exam.entity.TestManagement.Status;
import com.exam.exception.TestNotFoundException;
import com.exam.exception.TestNotStartedException;
import com.exam.service.TestManagementService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;



@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class TestManagementControllerTest {
	
	    
	    @Mock
	    private TestManagementService testManagementService;

	    @InjectMocks
	    private TestManagementController testManagementController;

	    @Autowired
	    private MockMvc mockMvc;
	    
	    ObjectMapper objectMapper = new ObjectMapper();
	    
	    @BeforeEach
	    public void setUp() {
	        mockMvc = MockMvcBuilders.standaloneSetup(testManagementController).build();
	        MockitoAnnotations.openMocks(this);
	    }
	    
	    @Test
		@DisplayName("Enroll Test Testing Successfuliy")
		void testEnrollTestSuccess() throws  Exception {
			
			objectMapper.registerModule(new JavaTimeModule());
			
			TestManagementDto tm=new TestManagementDto();
			tm.setPrevious_question_id(2L);
			tm.setStatement("Test case");
			tm.setOption1("opt1");
			tm.setOption2("opt2");
			tm.setOption3("opt3");
			tm.setOption4("opt4");
			tm.setId(2L);
			tm.setObtainedScore(80);
			tm.setStatus(Status.ENROLLED);
			tm.setTotalTimeTaken(LocalTime.parse("12:10:00"));
			tm.setUserid(3L);
			tm.setTestid(5L);
			tm.setQuestionid(6L);
			tm.setResult("80");
			
			
			
			when(testManagementService.enrollTest(3L,5L)).thenReturn("Enrolled successfully");
			

			mockMvc.perform(post("/enroll-test").contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(tm))).andExpect(status().isOk())
					.andExpect(content().string("Enrolled successfully"));	

		}
		
//		@Test
//		@DisplayName("Enroll Test Testing Failed")
//		void testEnrollTestFail() throws  Exception {
//			
//			objectMapper.registerModule(new JavaTimeModule());
//			
//			TestManagementDto tm=new TestManagementDto();
//			tm.setPrevious_question_id(2L);
//			tm.setStatement("Test case");
//			tm.setOption1("opt1");
//			tm.setOption2("opt2");
//			tm.setOption3("opt3");
//			tm.setOption4("opt4");
//			tm.setId(2L);
//			tm.setObtainedScore(80);
//			tm.setStatus(Status.ENROLLED);
//			tm.setTotalTimeTaken(LocalTime.parse("12:10:00"));
//			tm.setUserid(3L);
//			tm.setTestid(5L);
//			tm.setQuestionid(6L);
//			tm.setResult("80");
//			
//			
//			
//			when(testManagementService.enrollTest(3L,5L)).thenReturn("Enrolled successfully");
//			
//
//			mockMvc.perform(post("/enrolled-test").contentType(MediaType.APPLICATION_JSON)
//					.content(objectMapper.writeValueAsString(tm))).andExpect(status().isOk())
//					.andExpect(content().string("Intentionally changing the expected string"));	
//
//		}
		
		
		@Test
		@DisplayName("Enrolled Test testing Successfully")
		void testEnrolledTestPass() throws Exception{
			
			objectMapper.registerModule(new JavaTimeModule());
			TestManagementDto tm=new TestManagementDto();
			
			tm.setPrevious_question_id(2L);
			tm.setStatement("Test case");
			tm.setOption1("opt1");
			tm.setOption2("opt2");
			tm.setOption3("opt3");
			tm.setOption4("opt4");
			tm.setId(2L);
			tm.setObtainedScore(80);
			tm.setStatus(Status.ENROLLED);
			tm.setTotalTimeTaken(LocalTime.parse("12:10:00"));
			tm.setUserid(3L);
			tm.setTestid(5L);
			tm.setQuestionid(6L);
			tm.setResult("80");
			
			
			
			List<String> expectedList =new ArrayList<>();
			when(testManagementService.enrolledTest(3L)).thenReturn(expectedList);

			mockMvc.perform(post("/enrolled-test").contentType(MediaType.APPLICATION_JSON)
			        .content(objectMapper.writeValueAsString(tm)))
			        .andExpect(status().isOk())
			        .andExpect(content().json(objectMapper.writeValueAsString(expectedList)));
		
		}
		
//		@Test
//		@DisplayName("Enrolled Test testing Failed")
//		void testEnrolledTestfail() throws Exception{
//			
//			objectMapper.registerModule(new JavaTimeModule());
//			TestManagementDto tm=new TestManagementDto();
//			
//			tm.setPrevious_question_id(2L);
//			tm.setStatement("Test case");
//			tm.setOption1("opt1");
//			tm.setOption2("opt2");
//			tm.setOption3("opt3");
//			tm.setOption4("opt4");
//			tm.setId(2L);
//			tm.setObtainedScore(80);
//			tm.setStatus(Status.ENROLLED);
//			tm.setTotalTimeTaken(LocalTime.parse("12:10:00"));
//			tm.setUserid(3L);
//			tm.setTestid(5L);
//			tm.setQuestionid(6L);
//			tm.setResult("80");
//			List<String> expectedList =new ArrayList<>();
//			
//			when(testManagementService.enrolledTest(3L)).thenReturn(expectedList);
//			
//			List<String> actual=testManagementService.enrolledTest(3L);
//			assertNotEquals(expectedList, actual);
//
//			mockMvc.perform(post("/enrolled-test").contentType(MediaType.APPLICATION_JSON)
//			        .content(objectMapper.writeValueAsString(tm)))
//			        .andExpect(status().isOk())
//			        .andExpect(content().json(objectMapper.writeValueAsString(expectedList)));
//		
//		}
		
		@Test
		@DisplayName("Enrolled Student Testing Successfull")
		void testEnrolledStudentSuccess() throws Exception{
			
			objectMapper.registerModule(new JavaTimeModule());
			TestManagementDto tm=new TestManagementDto();
			
			tm.setPrevious_question_id(2L);
			tm.setStatement("Test case");
			tm.setOption1("opt1");
			tm.setOption2("opt2");
			tm.setOption3("opt3");
			tm.setOption4("opt4");
			tm.setId(2L);
			tm.setObtainedScore(80);
			tm.setStatus(Status.ENROLLED);
			tm.setTotalTimeTaken(LocalTime.parse("12:10:00"));
			tm.setUserid(3L);
			tm.setTestid(5L);
			tm.setQuestionid(6L);
			tm.setResult("80");
			
			
			
			List<String> expectedList = new ArrayList<>();
			
			when(testManagementService.enrolledStudent(5L)).thenReturn(expectedList);
			List<String> actual=testManagementService.enrolledStudent(5L);
			
			assertEquals(expectedList, actual);
			

			mockMvc.perform(post("/enrolled-student").contentType(MediaType.APPLICATION_JSON)
			        .content(objectMapper.writeValueAsString(tm)))
			        .andExpect(status().isOk())
			        .andExpect(content().json(objectMapper.writeValueAsString(expectedList)));
		
		}
		
//		@Test
//		@DisplayName("Enrolled Student Testing Fail")
//		void testEnrolledStudentFail() throws Exception{
//			
//			objectMapper.registerModule(new JavaTimeModule());
//			TestManagementDto tm=new TestManagementDto();
//			
//			tm.setPrevious_question_id(2L);
//			tm.setStatement("Test case");
//			tm.setOption1("opt1");
//			tm.setOption2("opt2");
//			tm.setOption3("opt3");
//			tm.setOption4("opt4");
//			tm.setId(2L);
//			tm.setObtainedScore(80);
//			tm.setStatus(Status.ENROLLED);
//			tm.setTotalTimeTaken(LocalTime.parse("12:10:00"));
//			tm.setUserid(3L);
//			tm.setTestid(5L);
//			tm.setQuestionid(6L);
//			tm.setResult("80");
//			
//			
//			
//			List<String> expectedList = new ArrayList<>();
//			
//			when(testManagementService.enrolledStudent(5L)).thenReturn(expectedList);
//			List<String> actual=testManagementService.enrolledStudent(5L);
//			
//			assertNotEquals(expectedList, actual);
//			
//
//			mockMvc.perform(get("/enrolled-student").contentType(MediaType.APPLICATION_JSON)
//			        .content(objectMapper.writeValueAsString(tm)))
//			        .andExpect(status().isOk())
//			        .andExpect(content().json(objectMapper.writeValueAsString(expectedList)));
//		
//		}

	    
    @Test
    @DisplayName("Start Test Testing Successfully")
    public void testStartTestSuccess() throws Exception {

    	TestManagementDto testManagement = new TestManagementDto();
        testManagement.setTestid(1L);
        testManagement.setUserid(2L);
        Question question = new Question();

        when(testManagementService.startTest(any(Long.class), any(Long.class))).thenReturn(question);

        mockMvc.perform(post("/start-test")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(testManagement)))
                .andExpect(status().isOk());
    }

//    @Test
//    @DisplayName("Start Test Testing Failed")
//    public void testStartTestFail() throws Exception {
//      
//    	TestManagementDto testManagement = new TestManagementDto();
//        testManagement.setTestid(1L);
//        testManagement.setUserid(2L);
//        Question question = new Question();
//
//        when(testManagementService.startTest(any(Long.class), any(Long.class)))
//                .thenReturn(question)
//                .thenThrow(new TestNotFoundException("Test not found"));
//
//        mockMvc.perform(post("/start-test")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(new ObjectMapper().writeValueAsString(testManagement)))
//                .andExpect(status().isNotFound());
//    }
    
    @Test
    @DisplayName("Test Submmited Testing Successfully")
    public void testSubmitTestSuccess() throws Exception {

    	TestManagementDto testManagement = new TestManagementDto();
        testManagement.setTestid(1L);
        testManagement.setUserid(2L);

        when(testManagementService.submittest(any(Long.class), any(Long.class))).thenReturn("Test submitted successfully");

        mockMvc.perform(post("/submit-test")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(testManagement)))
                .andExpect(status().isOk())
                .andExpect(content().string("Test submitted successfully"));
    }

//    @Test
//    @DisplayName("Test Submmited Testing Failed")
//    public void testSubmitTestFail() throws Exception {
//
//    	TestManagementDto testManagement = new TestManagementDto();
//        testManagement.setTestid(1L);
//        testManagement.setUserid(2L);
//
//        when(testManagementService.submittest(any(Long.class), any(Long.class)))
//                .thenReturn(null)
//                .thenThrow(new TestNotStartedException("Test not started"))
//                .thenThrow(new TestNotFoundException("Test not found"));
//
//        mockMvc.perform(post("/submit-test")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(new ObjectMapper().writeValueAsString(testManagement)))
//                .andExpect(status().isBadRequest())
//                .andExpect(content().string("Test not started"))
//                .andExpect(status().isNotFound());
//    }


    @Test
    @DisplayName("Get Next Question Testing Successfully")
    public void testGetNextQuestionSuccess() throws Exception {
        TestManagementDto testManagementDto = new TestManagementDto();
        testManagementDto.setQuestionid(1L);
        testManagementDto.setUserid(1L);
        testManagementDto.setTestid(1L);
        testManagementDto.setResult("Correct");
        

        QuestionDto questionDto = new QuestionDto();
        questionDto.setId(2L);
        questionDto.setStatement("Are you Happy");
        questionDto.setOption1("Yes");
        questionDto.setOption2("NO");
        questionDto.setOption3("May Be");
        questionDto.setOption4("May Not Be");
        
        when(testManagementService.getNextQuestion(1L, 1L, 1L, "Correct")).thenReturn(questionDto);

        MvcResult mvcResult = mockMvc.perform(post("/next-question")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(testManagementDto)))
                .andExpect(status().isOk())
                .andReturn();

        QuestionDto result = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), QuestionDto.class);
        assertEquals(questionDto, result);

        verify(testManagementService, times(1)).getNextQuestion(1L, 1L, 1L, "Correct");
    }

//    @Test
//    @DisplayName("Get Next Question Testing Failed")
//    public void testGetNextQuestionFail() throws Exception {
//        TestManagementDto testManagementDto = new TestManagementDto();
//        testManagementDto.setQuestionid(1L);
//        testManagementDto.setUserid(1L);
//        testManagementDto.setTestid(1L);
//        testManagementDto.setResult("Correct");
//        
//
//        QuestionDto questionDto = new QuestionDto();
//        questionDto.setId(2L);
//        questionDto.setStatement("Are you Happy");
//        questionDto.setOption1("Yes");
//        questionDto.setOption2("NO");
//        questionDto.setOption3("May Be");
//        questionDto.setOption4("May Not Be");
//        
//        when(testManagementService.getNextQuestion(1L, 1L, 1L, "Correct")).thenReturn(questionDto);
//
//        MvcResult mvcResult = mockMvc.perform(post("/next-question")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(new ObjectMapper().writeValueAsString(testManagementDto)))
//                .andExpect(status().isOk())
//                .andReturn();
//
//        QuestionDto result = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), QuestionDto.class);
//        assertEquals(questionDto, result);
//
//        verify(testManagementService, times(1)).getNextQuestion(1L, 2L, 1L, "Correct");
//    }

    @Test
    @DisplayName("Clear Response Testing Successfully")
    public void testClearResponseSuccess() throws Exception {
      
        Long userId = 1L;
        Long testId = 2L;
        Long questionId = 3L;
        String result = "example result";

        TestManagementDto testManagementDto = new TestManagementDto();
        testManagementDto.setUserid(userId);
        testManagementDto.setTestid(testId);
        testManagementDto.setQuestionid(questionId);
        testManagementDto.setResult(result);

        Mockito.when(testManagementService.updateResponse(userId, testId, questionId, result))
            .thenReturn("success");

        mockMvc.perform(MockMvcRequestBuilders.post("/clear-response")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(testManagementDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("success"));
    }
//    @Test
//    @DisplayName("Clear Response Testing Failed")
//    public void testClearResponseFail() throws Exception {
//      
//        Long userId = 1L;
//        Long testId = 2L;
//        Long questionId = 3L;
//        String result = "example result";
//
//        TestManagementDto testManagementDto = new TestManagementDto();
//        testManagementDto.setUserid(userId);
//        testManagementDto.setTestid(testId);
//        testManagementDto.setQuestionid(questionId);
//        testManagementDto.setResult(result);
//
//        Mockito.when(testManagementService.updateResponse(userId, testId, questionId, result))
//            .thenReturn("success");
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/clear-response")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(new ObjectMapper().writeValueAsString(testManagementDto)))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.content().string("Failed"));
//    }
    
    @Test
    @DisplayName("Update Response Testing Successfully")
    public void testUpdateResponseSuccess() throws Exception {
        // Create a TestManagementDto object with the necessary parameters
        TestManagementDto testManagementDto = new TestManagementDto();
        testManagementDto.setUserid(1L);
        testManagementDto.setTestid(2L);
        testManagementDto.setQuestionid(3L);
        testManagementDto.setResult("test result");
        
        // Mock the TestManagementService's updateResponse method to return a success message
        when(testManagementService.updateResponse(anyLong(), anyLong(), anyLong(), anyString()))
            .thenReturn("Response updated successfully");
        
        // Send a POST request to the /updateresponse endpoint with the TestManagementDto object as the request body
        mockMvc.perform(post("/update-response")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(testManagementDto)))
            .andExpect(status().isOk())
            .andExpect(content().string("Response updated successfully"));
    }
//    @Test
//    @DisplayName("Update Response Testing Failed")
//    public void testUpdateResponseFail() throws Exception {
//        // Create a TestManagementDto object with the necessary parameters
//        TestManagementDto testManagementDto = new TestManagementDto();
//        testManagementDto.setUserid(1L);
//        testManagementDto.setTestid(2L);
//        testManagementDto.setQuestionid(3L);
//        testManagementDto.setResult("test result");
//        
//        // Mock the TestManagementService's updateResponse method to return a success message
//        when(testManagementService.updateResponse(anyLong(), anyLong(), anyLong(), anyString()))
//            .thenReturn("Response updated successfully");
//        
//        // Send a POST request to the /updateresponse endpoint with the TestManagementDto object as the request body
//        mockMvc.perform(post("/update-response")
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(new ObjectMapper().writeValueAsString(testManagementDto)))
//            .andExpect(status().isOk())
//            .andExpect(content().string("Response update Failed"));
//    }
    
    @Test
    @DisplayName("Save Response Testing successfully")
    public void testSaveResponseSuccess() throws Exception {
        // Arrange
        TestManagementDto testManagementDto = new TestManagementDto();
        testManagementDto.setUserid(1L);
        testManagementDto.setTestid(2L);
        testManagementDto.setQuestionid(3L);
        testManagementDto.setResult("Answer");

        String expectedResponse = "Response saved successfully.";

        when(testManagementService.updateResponse(eq(1L), eq(2L), eq(3L), eq("Answer")))
            .thenReturn(expectedResponse);

        // Act and Assert
        mockMvc.perform(post("/save-response")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(testManagementDto)))
            .andExpect(status().isOk())
            .andExpect(content().string(expectedResponse));
    }
    
//    
//    @Test
//    @DisplayName("Save Response Testing Failed")
//    public void testSaveResponseFail() throws Exception {
//        // Arrange
//        TestManagementDto testManagementDto = new TestManagementDto();
//        testManagementDto.setUserid(1L);
//        testManagementDto.setTestid(2L);
//        testManagementDto.setQuestionid(3L);
//        testManagementDto.setResult("Answer");
//
//        String expectedResponse = "Response saved successfully.";
//
//        when(testManagementService.updateResponse(eq(1L), eq(2L), eq(3L), eq("Answer")))
//            .thenReturn(expectedResponse);
//
//        // Act and Assert
//        mockMvc.perform(post("/save-response")
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(new ObjectMapper().writeValueAsString(testManagementDto)))
//            .andExpect(status().isOk())
//            .andExpect(content().string("Save Response Failed"));
//    }


    @Test
    @DisplayName("Previous Question Testing Successfully")
        public void testPreviousQuestionSuccess() throws Exception {
        Long userId = 1L;
        Long questionId = 2L;
        TestManagementDto testManagementDto = new TestManagementDto();
        testManagementDto.setUserid(userId);
        testManagementDto.setQuestionid(questionId);

        QuestionDto expectedQuestion = new QuestionDto();
        expectedQuestion.setId(2L);
        expectedQuestion.setStatement("What is the capital of India?");
        expectedQuestion.setOption1("Banglore");
        expectedQuestion.setOption2("Delhi");
        expectedQuestion.setOption3("Odisha");
        expectedQuestion.setOption4("Mumbai");
        
        when(testManagementService.previousQuestion(userId, questionId)).thenReturn(expectedQuestion);

      
        mockMvc.perform(post("/previous-question")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testManagementDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedQuestion)));

        verify(testManagementService, times(1)).previousQuestion(userId, questionId);
    }
//       @Test
//       @DisplayName("Previous Question Testing Failed")
//       public void testPreviousQuestionFail() throws Exception {
//        Long userId = 1L;
//        Long questionId = 2L;
//        TestManagementDto testManagementDto = new TestManagementDto();
//        testManagementDto.setUserid(userId);
//        testManagementDto.setQuestionid(questionId);
//
//        QuestionDto expectedQuestion = new QuestionDto();
//        expectedQuestion.setId(2L);
//        expectedQuestion.setStatement("What is the capital of India?");
//        expectedQuestion.setOption1("Banglore");
//        expectedQuestion.setOption2("Delhi");
//        expectedQuestion.setOption3("Odisha");
//        expectedQuestion.setOption4("Mumbai");
//        
//        when(testManagementService.previousQuestion(userId, questionId)).thenReturn(expectedQuestion);
//
//      
//        mockMvc.perform(post("/previous-question")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(testManagementDto)))
//                .andExpect(status().isOk())
//                .andExpect(content().json(objectMapper.writeValueAsString(expectedQuestion)));
//
//        verify(testManagementService, times(1)).previousQuestion(2L, 2L);
//    }
    
}

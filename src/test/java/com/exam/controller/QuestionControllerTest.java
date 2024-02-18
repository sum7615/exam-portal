package com.exam.controller;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.exam.dto.QuestionDto;
import com.exam.entity.Question;
import com.exam.service.QuestionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class QuestionControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();
    
    @Mock
    private QuestionService questionService;
    
    @InjectMocks
    private QuestionController questionController;
    
    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(questionController).build();
    }
    
    @Test
    @DisplayName("Question Add Testing Successfully")
    public void testAddQuestionSuccess() throws  Exception{
        Question question = new Question();
        question.setStatement("How are you?");
        question.setOption1("Fine");
        question.setOption2("Not Fine");
        question.setOption3("May be");
        question.setOption4("May not be");
        question.setCorrectans(Question.correct_answer.option2);
        question.setId(1);
        question.setQuestionbankid(2);

        String expectedResponse="Success";
        when(questionService.add_question(any(Question.class))).thenReturn("Success");
        
        mockMvc.perform(post("/question")
                .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(question)))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResponse));
    }
    @Test
    @DisplayName("Question Add Testing Failed")
    public void testAddQuestionFail() throws  Exception{
        Question question = new Question();
        question.setStatement("How are you?");
        question.setOption1("Fine");
        question.setOption2("Not Fine");
        question.setOption3("May be");
        question.setOption4("May not be");
        question.setCorrectans(Question.correct_answer.option2);
        question.setId(1);
        question.setQuestionbankid(2);

        String expectedResponse="Success";
        when(questionService.add_question(any(Question.class))).thenReturn(expectedResponse);
        
        mockMvc.perform(post("/add-question")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(question)))
                .andExpect(status().isOk())
                .andExpect(content().string("Question bank id is not valid"));
    }
    
   
    @Test
    @DisplayName("Question Edit Testing Successfully")
    public void testEditQuestionSuccess() throws JsonProcessingException, Exception{
        Question question = new Question();
        question.setStatement("How are you?");
        question.setOption1("Fine");
        question.setOption2("Not Fine");
        question.setOption3("May be");
        question.setOption4("May not be");
        question.setCorrectans(Question.correct_answer.option3);
        question.setId(1);
        question.setQuestionbankid(2);

        String expectedResponse="Question updated successfully";
        when(questionService.edit_question(any(Question.class))).thenReturn(expectedResponse);
        
        mockMvc.perform(put("/question")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(question)))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedResponse));
    }
    @Test
    @DisplayName("Question Edit Testing Failed")
    public void testEditQuestionFail() throws JsonProcessingException, Exception{
        Question question = new Question();
        question.setStatement("How are you?");
        question.setOption1("Fine");
        question.setOption2("Not Fine");
        question.setOption3("May be");
        question.setOption4("May not be");
        question.setCorrectans(Question.correct_answer.option3);
        question.setId(1);
        question.setQuestionbankid(2);

        String expectedResponse="Question updated successfully";
        when(questionService.edit_question(any(Question.class))).thenReturn(expectedResponse);
        
        mockMvc.perform(put("/edit-question")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(question)))
                .andExpect(status().isOk())
                .andExpect(content().string("Id is not valid"));
    }

    @Test
    @DisplayName("Question Delete Testing Successfully")
    public void testDeleteQuestionSuccess() throws  Exception {
    	QuestionDto question = new QuestionDto();
    	question.setId(1L);
    	when(questionService.delete_question(1L))
    	.thenReturn("Question Bank deleted successfully");
    	mockMvc.perform(MockMvcRequestBuilders.delete("/delete-question", 1L));
 	    String result = questionService.delete_question(1L);
    	assertEquals("Question Bank deleted successfully", result);
    	}
    
    @Test
    @DisplayName("Question Delete Testing Failed")
    public void testDeleteQuestionFail() throws  Exception {
    	QuestionDto question = new QuestionDto();
    	question.setId(1L);
    	when(questionService.delete_question(1L))
    	.thenReturn("Question Bank deleted successfully");
        mockMvc.perform(MockMvcRequestBuilders.delete("/delete-question", 1L));
    	String result = questionService.delete_question(2L);
    	assertEquals("Question Id Not Found", result);
    	}

    @Test
    @DisplayName("Question View Testing Successfully")
    public void testViewQuestionSuccess() throws Exception {
        Question mockQuestion = new Question();
        mockQuestion.setQuestionbankid(123L);
        
        List<Question> mockQuestions = new ArrayList<>();
        mockQuestions.add(mockQuestion);
        
        when(questionService.view_question(123L)).thenReturn(mockQuestions);
        
        Question requestQuestion = new Question();
        requestQuestion.setQuestionbankid(123L);
        
        List<Question> responseQuestions = questionController.view_question(requestQuestion);
        mockMvc.perform(MockMvcRequestBuilders.post("/view-question"));
        assertEquals(mockQuestions, responseQuestions);
        verify(questionService, times(1)).view_question(123L);
    }
    
//    @Test
//    @DisplayName("Question View Testing Failed")
//    public void testViewQuestionFail() throws Exception {
//        when(questionService.view_question(123L))
//        .thenThrow(new RuntimeException("Question not found"));
//        
//        Question requestQuestion = new Question();
//        requestQuestion.setQuestionbankid(123L);
//        mockMvc.perform(MockMvcRequestBuilders.post("/view-question"));
//        assertThrows(RuntimeException.class, () -> {
//            questionController.view_question(requestQuestion);
//        });
//        verify(questionService, times(1)).view_question(456L);
//    }
} 
     
     
     
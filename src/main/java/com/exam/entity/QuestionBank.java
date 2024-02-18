package com.exam.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionBank {
	
@Id
@GeneratedValue(strategy = GenerationType.SEQUENCE)
 private long qbid;
@NotBlank(message ="QuestionBank name is not valid")
 private String name;
 private List<Long> questionid;
 
 @OneToOne(mappedBy = "questionBank")
private Test test;

}

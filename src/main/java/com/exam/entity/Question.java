package com.exam.entity;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Question {

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Column(name="questionbankid")
	private long questionbankid;
	
	@NotNull
	@Column(unique=true)
	@NotBlank(message="Statement should not be null")
	private String statement;
	
	@NotBlank(message="Option is not valid")
	private String option1;
	
	@NotBlank(message="Option is not valid")
	private String option2;
	
	@NotBlank(message="Option is not valid")
	private String option3;
	
	@NotBlank(message="Option is not valid")
	private String option4;
	
	@Enumerated(EnumType.STRING)			//ENTRY-1
	@Column(name = "correctans")
	private correct_answer correctans;

	public enum correct_answer{
		option1, 
		option2,
		option3,
		option4
		}

}

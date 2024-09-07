package com.exam.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Getter
@Setter

@Table(name="telephone")
@SequenceGenerator(name = "telephone_seq", sequenceName = "telephone_seq", allocationSize = 1)
public class Telephone {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "telephone_seq")
    private Long id;
	private String phoneNumber;
	private String phoneType;
	
}

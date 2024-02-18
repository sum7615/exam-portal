package com.exam.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Data
@Entity
@EqualsAndHashCode
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Table(name="users", uniqueConstraints = @UniqueConstraint(name = "username_unique", columnNames = "username"))
public class User{

	@Id

    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    @Column(name = "id", updatable = false)
    private Long id;
	
	
	@NotBlank(message = "Name is not valid")
//	@Pattern(regexp ="^[a-zA-Z][a-zA-Z\\s]{0,20}[a-zA-Z]$")
	private String name;
	
	
	@Column(unique=true, name="username")
	@NotBlank(message = "Username is not valid")
//   @UniqueElements(message="Username is duplicate")

	
	private String username;
	private String phone;
	private String address;
	private String password;

	
	private String roles;

}

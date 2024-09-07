package com.exam.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
//
//	private long id;
//
//	@NotBlank
//	@Pattern(regexp = "^[a-zA-Z]+[a-zA-Z\\s]*[a-zA-Z]+$", message = "Name must contain only letters and spaces")
//	private String name;
//
//	@NotBlank
//	@Pattern(regexp = "^[a-zA-Z0-9_]+@+[a-zA-Z]+.+[a-zA-Z]$", message = "Username must start with uppercase or lowercase letter and contain one @ and . also like abc@xyz.com")
//	private String username;
//
//	@NotBlank
//	@Pattern(regexp = "^(\\+91[\\-\\s]?)?[0]?(91)?[789]\\d{9}$", message = "Phone number must be a valid Indian number")
//	private String phone;
//
//	@NotBlank
//	@Pattern(regexp = "^[\\w\\s,.#-]+,[\\w\\s]+,[\\w\\s]+,[A-Za-z\\s]+,[0-9]+$", message = "Address must be in the format: street, city, state, country, zipcode. Ex: North street,Banglore,Karnataka,India,799130")
//	private String address;
//
//	@NotBlank
//	@Size(min = 8, max= 30)
//	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", message = "Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character")
//	private String password;
//     
//
//	private String roles;
	
	 private String firstName;
	    private String midleName;
	    private String lastName;
	    private String userEmail;
	    private String phone;
	    private String password;
	    private String userName;
	
	
	
}

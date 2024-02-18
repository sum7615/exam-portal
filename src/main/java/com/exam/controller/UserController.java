package com.exam.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.exam.dto.ResgistrationResponseDto;
import com.exam.dto.SingleUserReturn;
import com.exam.dto.UpdateUserDto;
import com.exam.dto.UserChangePasswordDto;
import com.exam.dto.UserDto;
import com.exam.entity.User;
import com.exam.exception.UserAlreadyExistException;
import com.exam.exception.UserNotFoundException;
import com.exam.service.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
public class UserController {

	@Autowired
	private UserService userService;
	Logger log = LoggerFactory.getLogger(QuestionBankController.class);

	@SecurityRequirement(name = "Bearer Authentication")
	@PreAuthorize(value = "hasRole('ROLE_ADMIN')")
	@DeleteMapping("/user")
	public String deleteuser(@RequestBody UserDto userDto) throws UserNotFoundException {
//		return userService.deleteuser(userDto.getId());
		Long userId = userDto.getId();
	    log.debug("Deleting user with id: {}", userId);
	    try {
	        String result = userService.deleteuser(userId);
	        log.debug("User deleted successfully with id: {}", userId);
	        return result;
	    } catch (UserNotFoundException e) {
	        log.error("Failed to delete user with id: {}. User not found.", userId);
	        throw e;
	    }

	}

	@SecurityRequirement(name = "Bearer Authentication")
	@PreAuthorize(value = "hasRole('ROLE_ADMIN') || hasRole('ROLE_USER')")
	@PutMapping("/user")
	public User updateUser(@Valid @RequestBody UpdateUserDto userDto) throws UserNotFoundException{
//		return userService.updateUser(userDto);
		log.debug("Update user request received with data: {}", userDto.toString());
        User updatedUser = userService.updateUser(userDto);
        return updatedUser;
	}

	@SecurityRequirement(name = "Bearer Authentication")
	@PreAuthorize(value = "hasRole('ROLE_ADMIN') || hasRole('ROLE_USER')")
	@PostMapping("/change-password")
	public String changePassword(@Valid @RequestBody UserChangePasswordDto userChangePasswordDto)throws UserNotFoundException {
		return userService.changePassword(userChangePasswordDto);
	   
	}
	
	
	@SecurityRequirement(name = "Bearer Authentication")
	@PreAuthorize(value = "hasRole('ROLE_ADMIN')")
	@GetMapping("/users")
	public List<ResgistrationResponseDto> alluser(){
		return userService.alluser();

	}
	@SecurityRequirement(name = "Bearer Authentication")
	@PreAuthorize(value = "hasRole('ROLE_ADMIN') || hasRole('ROLE_USER')")
	@GetMapping("/user/{id}")
	public SingleUserReturn user(@PathVariable long id) throws UserNotFoundException{
		return userService.user(id);
	}

//	// Clear JWT token stored in a cookie
//	private void clearJwtCookie(HttpServletRequest request, HttpServletResponse response) {
//		Cookie[] cookies = request.getCookies();
//		if (cookies != null) {
//			for (Cookie cookie : cookies) {
//				if (cookie.getName().equals("jwt")) {
//					cookie.setValue("");
//					cookie.setPath("/");
//					cookie.setMaxAge(0);
//					response.addCookie(cookie);
//					break;
//				}
//			}
//		}
//	}

	@PostMapping("/register")
	public ResponseEntity<Object> register(@Valid @RequestBody UserDto userDto, BindingResult result)
			throws TransactionSystemException, UserAlreadyExistException {
		if (result.hasErrors()) {
			StringBuilder sb = new StringBuilder();
			result.getFieldErrors()
					.forEach(error -> sb.append(error.getField() + ": " + error.getDefaultMessage() + "\n"));
			return ResponseEntity.badRequest().body(sb.toString());
		}
		// converting DTO to entity
		User user = new User();
		user.setAddress(userDto.getAddress());
		user.setName(userDto.getName());
		String encryptedPassword = new BCryptPasswordEncoder().encode(userDto.getPassword());
		user.setPassword(encryptedPassword);
		user.setUsername(userDto.getUsername());
		user.setPhone(userDto.getPhone());
		user.setRoles(userDto.getRoles());
		ResgistrationResponseDto savedUser = userService.register(user);
		
		//calling service 
		return ResponseEntity.ok(savedUser);
	}

}

package com.exam.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.PathVariable;

import com.exam.dto.ResgistrationResponseDto;
import com.exam.dto.SingleUserReturn;
import com.exam.dto.UpdateUserDto;
import com.exam.dto.UserChangePasswordDto;
import com.exam.entity.User;
import com.exam.exception.UserAlreadyExistException;
import com.exam.exception.UserNotFoundException;

@Service
public interface UserService {
	public ResgistrationResponseDto register(User user)throws TransactionSystemException, UserAlreadyExistException;
	public String deleteuser(Long id) throws UserNotFoundException;
	public User updateUser(UpdateUserDto updatedUser) throws UserNotFoundException;
	public String changePassword(UserChangePasswordDto userChangePasswordDto)throws UserNotFoundException;
	public List<ResgistrationResponseDto> alluser();
	SingleUserReturn user(@PathVariable long id) throws UserNotFoundException;
}

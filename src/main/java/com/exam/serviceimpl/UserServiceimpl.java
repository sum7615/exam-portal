package com.exam.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;

import com.exam.dto.ResgistrationResponseDto;
import com.exam.dto.SingleUserReturn;
import com.exam.dto.UpdateUserDto;
import com.exam.dto.UserChangePasswordDto;
import com.exam.entity.User;
import com.exam.exception.UserAlreadyExistException;
import com.exam.exception.UserNotFoundException;
import com.exam.repository.UsersRepository;
import com.exam.service.UserService;

@Service
public class UserServiceimpl implements UserService {
	@Autowired
	UsersRepository userRepository;

	@Override
	public ResgistrationResponseDto register(User user) throws TransactionSystemException, UserAlreadyExistException {
		if (userRepository.findByUsername(user.getUsername()).isEmpty()) {
			userRepository.save(user);
			ResgistrationResponseDto dto = new ResgistrationResponseDto();
			dto.setAddress(user.getAddress());
			dto.setId(userRepository.findByUsername(user.getUsername()).get().getId());
			dto.setName(user.getName());
			dto.setPhone(user.getPhone());
			dto.setRoles(user.getRoles());
			dto.setUsername(user.getUsername());

			return dto;

		} else {

			throw new UserAlreadyExistException("User is already registered");

		}

	}

	@Override
	public String deleteuser(Long id) throws UserNotFoundException {
		User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User Not Found"));

		userRepository.deleteById(id);
		return "Deleted Successfully";

	}

	@Override
	public User updateUser(UpdateUserDto updatedUser) throws UserNotFoundException {
		User user = userRepository.findById(updatedUser.getId())
				.orElseThrow(() -> new UserNotFoundException("User Not Found"));

		user.setName(updatedUser.getName());
		user.setPhone(updatedUser.getPhone());
		user.setAddress(updatedUser.getAddress());
		userRepository.save(user);

		return user;

	}

	@Override
	public String changePassword(UserChangePasswordDto userChangePasswordDto) throws UserNotFoundException {

		User user = userRepository.findById(userChangePasswordDto.getId())
				.orElseThrow(() -> new UserNotFoundException("User Not Found"));
		user.setId(userChangePasswordDto.getId());
		
		String encryptedPassword = new BCryptPasswordEncoder().encode(userChangePasswordDto.getPassword());
		
		user.setPassword(encryptedPassword);
		userRepository.save(user);

		return "Password Changed";

	}

	@Override
	public List<ResgistrationResponseDto> alluser(){

			List<User> user = userRepository.findAll();
			List<ResgistrationResponseDto> alluser = user.stream()
			    .map(u -> new ResgistrationResponseDto(u.getId(), u.getName(), u.getPhone(), u.getRoles(), u.getAddress(), u.getUsername()))
			    .collect(Collectors.toList());
			return alluser;
			
		
	}

	@Override
	public SingleUserReturn user(long id) throws UserNotFoundException {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("User Not Found"));
		SingleUserReturn resturnUser = new SingleUserReturn();
		
		resturnUser.setAddress(user.getAddress());
		resturnUser.setId(user.getId());
		resturnUser.setName(user.getName());
		resturnUser.setPhone(user.getPhone());
		resturnUser.setUsername(user.getUsername());
		return resturnUser;
		
	}

}

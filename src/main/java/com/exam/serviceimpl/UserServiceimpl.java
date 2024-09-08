package com.exam.serviceimpl;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;

import com.exam.dto.ResgistrationResponseDto;
import com.exam.dto.SingleUserReturn;
import com.exam.dto.UpdateUserDto;
import com.exam.dto.UserChangePasswordDto;
import com.exam.entity.Action;
import com.exam.entity.Role;
import com.exam.entity.User;
import com.exam.exception.UserAlreadyExistException;
import com.exam.exception.UserNotFoundException;
import com.exam.repository.ActionRepository;
import com.exam.repository.EmailRepository;
import com.exam.repository.RoleRepository;
import com.exam.repository.UsersRepository;
import com.exam.service.UserService;
import com.exam.util.EmailService;

@Service
public class UserServiceimpl implements UserService {
	@Autowired
	UsersRepository userRepository;

	@Autowired
    private EmailRepository emailRepository;
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    EmailService emailService;
    
    @Autowired
    ActionRepository actionRepository;
    
	@Override
	public List<String> getAction(List<String> roleNames) {
		List<Role> roles = roleRepository.findByNameIn(roleNames);

		
        return roles.stream()
                    .flatMap(role -> role.getAction().stream())
                    .map(Action::getActionName)
                    .distinct()
                    .collect(Collectors.toList());
	}

    @Override
	public boolean checkUserName(String userName) {
    	return (userRepository.getByUserName(userName.toUpperCase()).isEmpty())?true:false;
	}
	@Override
	public ResgistrationResponseDto register(User user) throws TransactionSystemException, UserAlreadyExistException {
		if (emailRepository.findByEmailAddress(user.getUsername()).isEmpty()) {
	        
	        // Handle role
	        Optional<Role> roleOptional = roleRepository.findByName("user");
	        
	        Role role = roleOptional.orElseGet(() -> {
	            Role newRole = new Role();
	            newRole.setName("user");
	            return newRole;
	        });

	        user.getRoles().add(role);
	        user.setIsActive("N");
			userRepository.save(user);
			
			String subject ="Welcome to SPXAM, "+user.getFirstName();
			String htmlBody;
			try {
				String templatePath = "template/welcome_template.html";

				ClassPathResource resource = new ClassPathResource(templatePath);
	             htmlBody = new String(Files.readAllBytes(resource.getFile().toPath()));
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				htmlBody="Welcome to family.";
				
			}
			Set<String> toAddress =user.getEmails().stream().map(e->e.getEmailAddress()).collect(Collectors.toSet());
			
			emailService.sendSimpleEmail(toAddress, null, subject, htmlBody);
			
			ResgistrationResponseDto dto = new ResgistrationResponseDto();
			dto.setUserEmail(user.getUsername());
			dto.setRoles(user.getRoles().stream().map(e -> e.getName()).collect(Collectors.joining(",")));

			return dto;

		} else {

			throw new UserAlreadyExistException("User is already registered");

		}

	}
	/*
	 * @Override public String deleteuser(Long id) throws UserNotFoundException {
	 * User user = userRepository.findById(id).orElseThrow(() -> new
	 * UserNotFoundException("User Not Found"));
	 * 
	 * userRepository.deleteById(id); return "Deleted Successfully";
	 * 
	 * }
	 * 
	 * @Override public User updateUser(UpdateUserDto updatedUser) throws
	 * UserNotFoundException { User user =
	 * userRepository.findById(updatedUser.getId()) .orElseThrow(() -> new
	 * UserNotFoundException("User Not Found"));
	 * 
	 * user.setName(updatedUser.getName()); user.setPhone(updatedUser.getPhone());
	 * user.setAddress(updatedUser.getAddress()); userRepository.save(user);
	 * 
	 * return user;
	 * 
	 * }
	 * 
	 * @Override public String changePassword(UserChangePasswordDto
	 * userChangePasswordDto) throws UserNotFoundException {
	 * 
	 * User user = userRepository.findById(userChangePasswordDto.getId())
	 * .orElseThrow(() -> new UserNotFoundException("User Not Found"));
	 * user.setId(userChangePasswordDto.getId());
	 * 
	 * String encryptedPassword = new
	 * BCryptPasswordEncoder().encode(userChangePasswordDto.getPassword());
	 * 
	 * user.setPassword(encryptedPassword); userRepository.save(user);
	 * 
	 * return "Password Changed";
	 * 
	 * }
	 * 
	 * @Override public List<ResgistrationResponseDto> alluser(){
	 * 
	 * List<User> user = userRepository.findAll(); List<ResgistrationResponseDto>
	 * alluser = user.stream() .map(u -> new ResgistrationResponseDto(u.getId(),
	 * u.getName(), u.getPhone(), u.getRoles(), u.getAddress(), u.getUsername()))
	 * .collect(Collectors.toList()); return alluser;
	 * 
	 * 
	 * }
	 * 
	 * @Override public SingleUserReturn user(long id) throws UserNotFoundException
	 * { User user = userRepository.findById(id) .orElseThrow(() -> new
	 * UserNotFoundException("User Not Found")); SingleUserReturn resturnUser = new
	 * SingleUserReturn();
	 * 
	 * resturnUser.setAddress(user.getAddress()); resturnUser.setId(user.getId());
	 * resturnUser.setName(user.getName()); resturnUser.setPhone(user.getPhone());
	 * resturnUser.setUsername(user.getUsername()); return resturnUser;
	 * 
	 * }
	 */

	@Override
	public String deleteuser(Long id) throws UserNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User updateUser(UpdateUserDto updatedUser) throws UserNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String changePassword(UserChangePasswordDto userChangePasswordDto) throws UserNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ResgistrationResponseDto> alluser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SingleUserReturn user(long id) throws UserNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	
}

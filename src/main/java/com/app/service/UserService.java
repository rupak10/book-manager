package com.app.service;

import com.app.dto.AppResponse;
import com.app.dto.LoginRequest;
import com.app.model.User;
import com.app.repository.UserRepository;
import com.app.util.CommonUtil;
import com.app.util.RequestValidator;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

	private final UserRepository userRepository;


	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public AppResponse authenticateUser(LoginRequest loginRequest) {
		try {
			if(!RequestValidator.isLoginUpRequestValid(loginRequest)){
				return new AppResponse(false, "Invalid value provided !");
			}

			Optional<User> userOptional = userRepository.findByUsername(loginRequest.getUsername());
			if(userOptional.isEmpty()){
				return new AppResponse(false, "Wrong Credentials !");
			}

			if(!CommonUtil.isPasswordValid(loginRequest.getPassword(), userOptional.get().getPassword())){
				return new AppResponse(false, "Wrong Credentials !");
			}

			return new AppResponse(true, "Login Success.");
		}
		catch (Exception e) {
			e.printStackTrace();
			return new AppResponse(false, "Login Failed !");
		}
	}

/*	public AppResponse doRegistration(SignupRequest signupRequest) {
		try {
			if(!RequestValidator.isSignUpRequestValid(signupRequest)){
				return new AppResponse(false, "Invalid value provided !");
			}

			Optional<User> existingUser = userRepository.findByEmail(signupRequest.getEmail());
			if(existingUser.isPresent()) {
				return new AppResponse(false, "Email already exists !");
			}

			User user = new User();
			user.setFirstName(signupRequest.getFirstName());
			user.setSurname(signupRequest.getSurname());
			user.setEmail(signupRequest.getEmail());
			user.setPassword(CommonUtil.getEncodedPassword(signupRequest.getPassword()));
			user.setStatus(1);
			user.setRole("ROLE_USER");
			user.setCreatedAt(new Timestamp(System.currentTimeMillis()));

			userRepository.save(user);

			return new AppResponse(true, "Registration Success.");
		}
		catch (Exception e) {
			e.printStackTrace();
			return new AppResponse(false, "Registration Failed !");
		}
	}*/

	public User getByUsername(String username) {
		try {
			Optional<User> userOptional = userRepository.findByUsername(username);
            return userOptional.orElse(null);
        }
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


}

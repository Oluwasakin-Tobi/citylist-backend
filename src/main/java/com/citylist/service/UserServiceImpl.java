package com.citylist.service;

import com.citylist.entity.User;
import com.citylist.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	private final static String USER_NOT_FOUND_MSG = "user with email %s not found";

	@Override
	public User loadUserByUsername(String email) throws UsernameNotFoundException {

		return userRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
	}


	@Override
	public User addUser(User user) {
		String password = passwordEncoder.encode(user.getPassword());
		user.setPassword(password);
		
		User response = userRepository.save(user);
		return response;
	}

	

}

package com.citylist.controller;

import com.citylist.exception.UserNotFoundException;
import com.citylist.models.Response;
import com.citylist.service.UserService;
import com.citylist.util.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import com.citylist.entity.*;
import com.citylist.enums.ResponseStatus;

import io.swagger.annotations.Api;


@RestController
@RequiredArgsConstructor
@Slf4j
@Api(tags = {"Users"})
public class UserController {
	private final UserService userService;
	private final AuthenticationManager authenticationManager;
	private final JwtTokenUtil jwtTokenUtil;
	
	
	@PostMapping(value = "/users")
	public ResponseEntity<?> AddUser(@RequestBody User user) {
		User response = userService.addUser(user);
		
		return ResponseEntity.ok(new Response(response, ResponseStatus.SUCCESSFUL.getCode(), 
				ResponseStatus.SUCCESSFUL.getMessage(), ResponseStatus.SUCCESSFUL.getMessage()));
	}
	

	@PostMapping(value = "/authenticate")
	public ResponseEntity<?> login(@RequestBody JwtRequest jwtRequest) throws UserNotFoundException {

		try {
		authenticate(jwtRequest.getEmail(), jwtRequest.getPassword());
		}
		
		catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response(null, ResponseStatus.ERROR.getCode(), 
					"Email/Password is incorrect", ResponseStatus.ERROR.getMessage()));
		}

		final String token = getToken(jwtRequest.getEmail());
		
		return ResponseEntity.ok(new Response(token, ResponseStatus.SUCCESSFUL.getCode(), 
				ResponseStatus.SUCCESSFUL.getMessage(), ResponseStatus.SUCCESSFUL.getMessage()));
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException | BadCredentialsException e) {
			throw new Exception(e);
		} 
	}
	
	private String getToken(String username) {
		log.info("Email: "+username);
		final User userDetails = userService.loadUserByUsername(username);
		return jwtTokenUtil.generateToken(userDetails);
	}
	
	

}

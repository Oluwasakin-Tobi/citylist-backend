package com.citylist.util;

import java.io.Serializable;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class JwtRequest implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = -9173073632493801016L;
	
	private String email;
	private String password;
	private String confirmPassword;
	private String otp;

}

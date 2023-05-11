package com.citylist.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.web.client.RestTemplate;

@Configuration
public class PasswordEncoder {

	@Bean
	org.springframework.security.crypto.password.PasswordEncoder argonPasswordEncoder() {
		return new Argon2PasswordEncoder();
	};
	

	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}

}

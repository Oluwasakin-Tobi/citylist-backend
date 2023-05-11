package com.citylist.service;


import com.citylist.entity.User;

public interface UserService {

	User loadUserByUsername(String email);

	User addUser(User user);

	
}

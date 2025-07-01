package com.example.Billing.System.service;

import java.util.List;

import com.example.Billing.System.io.UserRequest;
import com.example.Billing.System.io.UserResponse;

public interface UserService {

	UserResponse createUser(UserRequest request);
		
	String getUserRole(String email);
	List<UserResponse> readUsers();
	void deleteuser(String id);
	
}

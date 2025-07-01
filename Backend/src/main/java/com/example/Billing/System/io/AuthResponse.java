package com.example.Billing.System.io;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor

public class AuthResponse {

	private String email;
	private String token;
	private String refreshToken;
	private String role;
	
	
}

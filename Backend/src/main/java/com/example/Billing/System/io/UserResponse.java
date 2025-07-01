package com.example.Billing.System.io;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UserResponse {

	
        String userId;
		String email;
		String name;
		LocalDateTime createdAt;
		LocalDateTime updatedAt; 
		String role;
	
}

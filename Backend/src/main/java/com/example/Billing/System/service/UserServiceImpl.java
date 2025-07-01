package com.example.Billing.System.service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.Billing.System.entity.UserEntity;
import com.example.Billing.System.io.UserRequest;
import com.example.Billing.System.io.UserResponse;
import com.example.Billing.System.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

	private final UserRepository userRepo;
	private final PasswordEncoder passwordEncoder;
	@Override
	public UserResponse createUser(UserRequest request) {
		// TODO Auto-generated method stub
		 UserEntity newUser = convertToEntity(request);
		 newUser = userRepo.save(newUser);
		return convertToResponse(newUser) ;
		
	}

	private UserResponse convertToResponse(UserEntity newUser) {
		// TODO Auto-generated method stub
		return UserResponse.builder()
                    .name(newUser.getName())
                    .email(newUser.getEmail())
                    .userId(newUser.getUserId())
                    .createdAt(newUser.getCreatedAt())
                    .updatedAt(newUser.getUpdatedAt())
                    .role(newUser.getRole())
                    .build();
                    
//		return null;
	}

	private UserEntity convertToEntity(UserRequest request) {
		// TODO Auto-generated method stub
		return UserEntity.builder()
		          .userId(UUID.randomUUID().toString())
		          .email(request.getEmail())
		          .password(passwordEncoder.encode(request.getPassword()))
		          .role(request.getRole().toUpperCase())
		          .name(request.getName())
		          .build();
	}

	@Override
	public String getUserRole(String email) {
		// TODO Auto-generated method stub
		UserEntity existingUser = userRepo.findByEmail(email)
		        .orElseThrow(()-> new UsernameNotFoundException("User not found for the email : "+ email));
		
		return existingUser.getRole();
	}

	@Override
	public List<UserResponse> readUsers() {
		// TODO Auto-generated method stub
		return userRepo.findAll()
				.stream()
				.map(user-> convertToResponse(user))
		        .collect(Collectors.toList());
	}

	@Override
	public void deleteuser(String id) {
		// TODO Auto-generated method stub
		UserEntity existingUser = userRepo.findByUserId(id)
		        .orElseThrow(() -> new UsernameNotFoundException("User not found with user id :" + id));
		  userRepo.delete(existingUser);      
		
	}

}

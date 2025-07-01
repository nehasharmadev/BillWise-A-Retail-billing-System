package com.example.Billing.System.service;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.Billing.System.entity.UserEntity;
import com.example.Billing.System.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService{

	private final UserRepository userRepo;
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		UserEntity existingUser = userRepo.findByEmail(email)
		        .orElseThrow(()-> new UsernameNotFoundException("user not found with email : " + email));
		return new User(existingUser.getEmail(), existingUser.getPassword(), 
				Collections.singleton(new SimpleGrantedAuthority(existingUser.getRole()))); 
	}

}

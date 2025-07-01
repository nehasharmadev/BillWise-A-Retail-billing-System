package com.example.Billing.System.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.Billing.System.Util.JwtUtil;
import com.example.Billing.System.io.AuthRequest;
import com.example.Billing.System.io.AuthResponse;
import com.example.Billing.System.service.UserService;

import lombok.RequiredArgsConstructor;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class AuthController {
	
	private final UserService userService;
	private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;
    private final UserDetailsService userDetailsService;
	@PostMapping("/encode")
	public String encodePassword( @RequestBody Map<String,String> request) {
		return passwordEncoder.encode(request.get("password"));
	}
	
//	@PostMapping("/login")
//	public AuthResponse login(@RequestBody AuthRequest request) throws Exception {
//		authenticate(request.getEmail(), request.getPassword());
//		final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
//		final String jwtToken = jwtUtil.generateToken(userDetails);
//		final String refreshToken = jwtUtil.generateRefreshToken(userDetails);
//		String role = userService.getUserRole(request.getEmail());
//		return new AuthResponse(request.getEmail(), jwtToken, refreshToken, role );
//		
//		
//	}
	
	

	

	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request, HttpServletResponse response) throws Exception {
	    authenticate(request.getEmail(), request.getPassword());
	    final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
	    final String jwtToken = jwtUtil.generateToken(userDetails);
	    final String refreshToken = jwtUtil.generateRefreshToken(userDetails);
	    String role = userService.getUserRole(request.getEmail());

	    // Set the refresh token in HttpOnly cookie
	    Cookie cookie = new Cookie("refreshToken", refreshToken);
	    cookie.setHttpOnly(true);
	    cookie.setSecure(false); 
	    cookie.setPath("/"); 
	    cookie.setMaxAge(7 * 24 * 60 * 60); 

	    response.addCookie(cookie);

	    AuthResponse authResponse = new AuthResponse(request.getEmail(), jwtToken, null, role);
	    return ResponseEntity.ok(authResponse);
	}

	 
	@PostMapping("/refresh")
	public ResponseEntity<AuthResponse> refresh(HttpServletRequest request) {
	    String refreshToken = null;

	    if (request.getCookies() != null) {
	        for (Cookie cookie : request.getCookies()) {
	            if ("refreshToken".equals(cookie.getName())) {
	                refreshToken = cookie.getValue();
	            }
	        }
	    }
//		 String refreshToken = request.get("refreshToken");
//		 String refreshToken = request.get("refreshToken");
	    if (refreshToken == null || !jwtUtil.validateRefreshToken(refreshToken)) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	    }
	    String username = jwtUtil.extractUsername(refreshToken);
	    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
	    
	    String newAccessToken = jwtUtil.generateToken(userDetails);
	    AuthResponse authResponse = new AuthResponse(username, newAccessToken, null, userService.getUserRole(username));
	    
	    return ResponseEntity.ok(authResponse);
	    }

	private void authenticate(String email, String password) throws Exception {
		// TODO Auto-generated method stub
		try {
			authManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
		}catch(DisabledException e) {
			throw new Exception("user disabled");
		}catch(BadCredentialsException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email or password is incorrect");
		}
		
	}
	
}

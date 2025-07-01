package com.example.Billing.System.config;

import java.util.List;

import org.springframework.web.filter.CorsFilter;

import com.example.Billing.System.Filters.JwtRequestFilter;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    
	private final JwtRequestFilter jwtRequestFilter;
	private final UserDetailsService userDeatilsService;
//	@Bean
//	public CorsFilter corsFilter() {
//		return new CorsFilter(corsConfigurationSource());
//	}

	private UrlBasedCorsConfigurationSource corsConfigurationSource() {
		// TODO Auto-generated method stub
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOrigins(List.of("http://localhost:4200"));
		config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
		config.setAllowedHeaders(List.of("Authorization", "Content-Type", "X-Requested-With", "Accept","Access-Control-Allow-Origin"));
		config.setAllowCredentials(true);
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);
		return source;
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.cors(cors -> cors.configurationSource(corsConfigurationSource())) 
        .csrf(AbstractHttpConfigurer::disable)
		    .authorizeHttpRequests(auth -> auth.requestMatchers("/login", "/encode", "/create-order","/verify", "/refresh").permitAll()
		    .requestMatchers("/category","/items","/api/v1.0/orders","/api/v1.0/getItems","/api/v1.0/payments","/api/v1.0/dashboard", "/api/v1.0/getCategories","/api/v1.0/subscribe","/api/v1.0/unsubscribe").hasAnyRole("USER", "ADMIN")
		    .requestMatchers("/api/v1.0/admin/**").hasRole("ADMIN")
		    .anyRequest().authenticated())
		    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		    .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
		    return http.build();
	}
	
	@Bean 
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authManager() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDeatilsService);
		authProvider.setPasswordEncoder(passwordEncoder());
		return new ProviderManager(authProvider);
	}
}





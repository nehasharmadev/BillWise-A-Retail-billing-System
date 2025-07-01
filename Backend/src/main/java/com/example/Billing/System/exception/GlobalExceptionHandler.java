package com.example.Billing.System.exception;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.Billing.System.service.CategoryServiceImpl;

import io.jsonwebtoken.ExpiredJwtException;

@ControllerAdvice
public class GlobalExceptionHandler {

	Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	@ExceptionHandler(ImageUploadException.class)
	public ResponseEntity<ErrorMessage> handleImageUploadException(ImageUploadException ex){
		ErrorMessage error = new ErrorMessage(
				HttpStatus.BAD_REQUEST.name(),
	            ex.getMessage(),
	            LocalDateTime.now());
		logger.error(ex.getMessage());	
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(CategorySaveException.class)
	public ResponseEntity<ErrorMessage> handleCategorySaveException(CategorySaveException ex){
		ErrorMessage error = new ErrorMessage(
				 HttpStatus.INTERNAL_SERVER_ERROR.name(),
		            "Something went wrong: " + ex.getMessage(), 
		            LocalDateTime.now());
		logger.error(ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(ImageDeleteException.class)
	public ResponseEntity<ErrorMessage> handleImageDeleteException(ImageDeleteException ex){
		ErrorMessage error = new ErrorMessage(
				HttpStatus.BAD_REQUEST.name(),
	            ex.getMessage(),
	            LocalDateTime.now());
		logger.error(ex.getMessage());	
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(CategoryNotFoundException.class)
	public ResponseEntity<ErrorMessage> handleCategoryNotFoundException(CategoryNotFoundException ex){
		ErrorMessage error = new ErrorMessage(
				 HttpStatus.NOT_FOUND.name(),
		            ex.getMessage(), 
		            LocalDateTime.now());
		logger.error(ex.getMessage());
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ErrorMessage>handleRuntimeException( RuntimeException ex){
		ErrorMessage error = new ErrorMessage(
				HttpStatus.INTERNAL_SERVER_ERROR.name(),
		            ex.getMessage(), 
		            LocalDateTime.now());
		logger.error(ex.getMessage());
		return new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
//	@ExceptionHandler(ExpiredJwtException.class)
//	public ResponseEntity<?> handleExpiredJwtException(ExpiredJwtException ex) {
//	    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token expired");
//	}
}  

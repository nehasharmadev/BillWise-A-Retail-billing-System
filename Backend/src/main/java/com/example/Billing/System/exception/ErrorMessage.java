package com.example.Billing.System.exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorMessage {

	private String status;
	private String message;
	private LocalDateTime timestamp;
}

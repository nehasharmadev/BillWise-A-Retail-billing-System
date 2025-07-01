package com.example.Billing.System.io;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
//@NoArgsConstructor
public class RazorpayOrderResponse {

	private String id;
	private String entity;
	private Integer amount;
	private String currency;
	private String status;
	private Date createdAt;
	private String receipt;
}

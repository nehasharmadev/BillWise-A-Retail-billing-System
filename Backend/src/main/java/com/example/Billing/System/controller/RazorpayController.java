package com.example.Billing.System.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.Billing.System.io.OrderResponse;
import com.example.Billing.System.io.PaymentRequest;
import com.example.Billing.System.io.PaymentVerificationRequest;
import com.example.Billing.System.io.RazorpayOrderResponse;
import com.example.Billing.System.service.OrderService;
import com.example.Billing.System.service.RazorpayService;
import com.razorpay.RazorpayException;

import org.springframework.http.HttpStatus;
import lombok.RequiredArgsConstructor;

@RestController
//@RequestMapping("/payments")
@RequiredArgsConstructor
//@CrossOrigin

public class RazorpayController {

	private final RazorpayService razorpayService;
	private final OrderService orderService;
	
	@PostMapping("/create-order")
	@ResponseStatus(HttpStatus.CREATED)
	public RazorpayOrderResponse createrazorpayOrder(@RequestBody PaymentRequest request) throws RazorpayException{
		return razorpayService.createOrder(request.getAmount(), request.getCurrency());
	}
	
	@PostMapping("/verify")
	public OrderResponse verifyPayment(@RequestBody PaymentVerificationRequest request) {
		return orderService.verifyPayment(request);
	}
	
}

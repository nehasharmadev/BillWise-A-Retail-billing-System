package com.example.Billing.System.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.Billing.System.io.OrderRequest;
import com.example.Billing.System.io.OrderResponse;
import com.example.Billing.System.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

	private final OrderService orderService;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public OrderResponse createOrder(@RequestBody OrderRequest request) {
		return orderService.createOrder(request);
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("admin/{orderId}")
	public void deleteOrder(@PathVariable String orderId) {
		orderService.deleteOrder(orderId);
	}
	
	@GetMapping("/latest")
	public List<OrderResponse> getLatestOrders(){
		return orderService.getLatestOrders();	}
}

package com.example.Billing.System.service;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.util.List;

import com.example.Billing.System.io.OrderRequest;
import com.example.Billing.System.io.OrderResponse;
import com.example.Billing.System.io.PaymentVerificationRequest;

public interface OrderService {

	OrderResponse createOrder(OrderRequest request);
	
	void deleteOrder(String orderId);
	
	List<OrderResponse> getLatestOrders();
	
	OrderResponse verifyPayment(PaymentVerificationRequest request);
	
	Double sumsalesByDate(LocalDate date);
	
	Long countByOrder(LocalDate date);
	
	List<OrderResponse> findRecentOrders();
	
	List<OrderResponse> getTodayOrders();
}

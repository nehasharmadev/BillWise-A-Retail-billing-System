package com.example.Billing.System.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.Billing.System.io.RazorpayOrderResponse;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import lombok.RequiredArgsConstructor;
//import lombok.Value;

@Service
@RequiredArgsConstructor
public class RazorpayServiceImpl implements RazorpayService{
	
	@Value("${razorpay.key.id}")
	private String razorpayKeyId;
	
	@Value("${razorpay.key.secret}")
	private String razorpayKeySecret;
	@Override
	public RazorpayOrderResponse createOrder(Double amount, String currency) throws RazorpayException {
		// TODO Auto-generated method stub
		RazorpayClient razorpayClient = new RazorpayClient(razorpayKeyId, razorpayKeySecret);
		JSONObject orderRequest = new JSONObject();
		orderRequest.put("amount", amount*100);
		orderRequest.put("currency", currency);
		orderRequest.put("receipt", "order_receipt" + System.currentTimeMillis());
		orderRequest.put("payment_capture", 1);
		Order order = razorpayClient.orders.create(orderRequest);
		return convertToResponse(order);
	}

	private RazorpayOrderResponse convertToResponse(Order order) {
		// TODO Auto-generated method stub
		return RazorpayOrderResponse.builder()
				                    .id(order.get("id"))
				                    .entity(order.get("entity"))
				                    .amount(order.get("amount"))
				                    .currency(order.get("currency"))
				                    .status(order.get("status"))
				                    .createdAt(order.get("created_at"))
				                    .receipt(order.get("receipt"))
				                    .build();
	}


}

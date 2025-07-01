package com.example.Billing.System.service;

import com.example.Billing.System.io.RazorpayOrderResponse;
import com.razorpay.RazorpayException;

public interface RazorpayService {

	 RazorpayOrderResponse createOrder(Double amount, String currency) throws RazorpayException;
}

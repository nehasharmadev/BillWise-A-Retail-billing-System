package com.example.Billing.System.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Billing.System.io.DashboardResponse;
import com.example.Billing.System.io.OrderResponse;
import com.example.Billing.System.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/dashboard")
@RequiredArgsConstructor
public class DashboardController {

	private final OrderService orderService;
	
	@GetMapping
	public DashboardResponse getDashboardData() {
		LocalDate today = LocalDate.now();
		Double todaySale = orderService.sumsalesByDate(today);
		Long todayOrderCount = orderService.countByOrder(today);
		List<OrderResponse> recentOrders = orderService.findRecentOrders();
		return new DashboardResponse(
				todaySale != null ? todaySale: 0.0,
				todayOrderCount != null ? todayOrderCount : 0,
						recentOrders);
	}
	
}

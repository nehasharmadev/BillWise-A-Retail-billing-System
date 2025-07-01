package com.example.Billing.System.service;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.Billing.System.entity.OrderEntity;
import com.example.Billing.System.entity.OrderItemEntity;
import com.example.Billing.System.io.OrderRequest;
import com.example.Billing.System.io.OrderResponse;
import com.example.Billing.System.io.PaymentDetails;
import com.example.Billing.System.io.PaymentMethod;
import com.example.Billing.System.io.PaymentVerificationRequest;
import com.example.Billing.System.repository.OrderEntityRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

	private final OrderEntityRepository orderEntityRepo;
	@Override
	public OrderResponse createOrder(OrderRequest request) {
		// TODO Auto-generated method stub
		OrderEntity newOrder = convertToOrderEntity(request);
		PaymentDetails paymentDetails = new PaymentDetails();
		paymentDetails.setStatus(newOrder.getPaymentMethod() == PaymentMethod.CASH?
				PaymentDetails.PaymentStatus.COMPLETED : PaymentDetails.PaymentStatus.PENDING);
		newOrder.setPaymentDetails(paymentDetails);
		List<OrderItemEntity> orderItems = request.getCartItems().stream()
		       .map(this::convertToOrderItemEntity)
		       .collect(Collectors.toList());
		newOrder.setItems(orderItems);
		newOrder = orderEntityRepo.save(newOrder);
		
		return convertToResponse(newOrder);
	}

	private OrderItemEntity convertToOrderItemEntity(OrderRequest.OrderItemRequest orderItemRequest) {
		return OrderItemEntity.builder()
				              .itemId(orderItemRequest.getItemId())
				              .name(orderItemRequest.getName())
				              .price(orderItemRequest.getPrice())
				              .quantity(orderItemRequest.getQuantity())
				              .build();
	}
	private OrderResponse convertToResponse(OrderEntity newOrder) {
		// TODO Auto-generated method stub
		return OrderResponse.builder()
		             .orderId(newOrder.getOrderId())
		             .customerName(newOrder.getCustomerName())
		             .phoneNumber(newOrder.getPhoneNumber())
		             .subtotal(newOrder.getSubtotal())
		             .tax(newOrder.getTax())
		             .grandTotal(newOrder.getGrandTotal())
		             .paymentMethod(newOrder.getPaymentMethod())
		             .items(newOrder.getItems().stream()
		            		        .map(this::convertToItemResponse)
		            		        .collect(Collectors.toList()))
		             .paymentDetails(newOrder.getPaymentDetails())
		             .createdAt(newOrder.getCreatedAt())
		             .build();
		
	}

	private OrderEntity convertToOrderEntity(OrderRequest request) {
		// TODO Auto-generated method stub
		return OrderEntity.builder()
		           .customerName(request.getCustomerName())
		           .phoneNumber(request.getPhoneNumber())
		           .subtotal(request.getSubtotal())
		           .tax(request.getTax())
		           .grandTotal(request.getGrandTotal())
		           .paymentMethod(request.getPaymentMethod())
		           .build();
	}

	private OrderResponse.OrderItemResponse convertToItemResponse(OrderItemEntity orderItemEntity){
		return OrderResponse.OrderItemResponse.builder()
				            .itemId(orderItemEntity.getItemId())
				            .name(orderItemEntity.getName())
				            .price(orderItemEntity.getPrice())
				            .quantity(orderItemEntity.getQuantity())
				            .build();
	}
	
	@Override
	public void deleteOrder(String orderId) {
		// TODO Auto-generated method stub
		OrderEntity existingOrder = orderEntityRepo.findByOrderId(orderId)
				                                   .orElseThrow(()-> new RuntimeException("Order not found!!"));
		orderEntityRepo.delete(existingOrder);
	}

	@Override
	public List<OrderResponse> getLatestOrders() {
		// TODO Auto-generated method stub
		return orderEntityRepo.findAllByOrderByCreatedAtDesc()
				              .stream()
				              .map(this::convertToResponse)
				              .collect(Collectors.toList());
		 
	}

	@Override
	public OrderResponse verifyPayment(PaymentVerificationRequest request) {
		// TODO Auto-generated method stub
		OrderEntity existingOrder = orderEntityRepo.findByOrderId(request.getOrderId())
				                    .orElseThrow(()-> new RuntimeException("order not found"));
		if(!verifyrazorpaySignature(request.getRazorpayOrderId(),
				                    request.getRazorpayPaymentId(),
				                    request.getRazorpaysignature())) {
			throw new RuntimeException("Payment verification failed!!");
		}
		PaymentDetails paymentDetails = existingOrder.getPaymentDetails();
		paymentDetails.setRazorpayOrderId(request.getRazorpayOrderId());
		paymentDetails.setRazorpayPaymentId(request.getRazorpayPaymentId());
		paymentDetails.setRazorpaySignature(request.getRazorpaysignature());
		paymentDetails.setStatus(PaymentDetails.PaymentStatus.COMPLETED);
	    existingOrder = orderEntityRepo.save(existingOrder);	
	    return convertToResponse(existingOrder);
	}

	private boolean verifyrazorpaySignature(String razorpayOrderId, String razorpayPaymentId,
			String razorpaysignature) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Double sumsalesByDate(LocalDate date) {
		// TODO Auto-generated method stub
		return orderEntityRepo.sumSalesByDate(date);
	}

	@Override
	public Long countByOrder(LocalDate date) {
		// TODO Auto-generated method stub
		return orderEntityRepo.countByOrderDate(date);
	}

	@Override
	public List<OrderResponse> findRecentOrders() {
		// TODO Auto-generated method stub
		return orderEntityRepo.findRecentorders(PageRequest.of(0, 5))
				.stream()
				.map(orderEntity-> convertToResponse(orderEntity))
				.collect(Collectors.toList());
	}

	@Override
	public List<OrderResponse> getTodayOrders() {
	    LocalDate today = LocalDate.now().minusDays(1);;

	    // Assuming you have a repository method to fetch orders by date
	    List<OrderEntity> todayOrders = orderEntityRepo.findByCreatedAtBetween(
	        today.atStartOfDay(),
	        today.plusDays(1).atStartOfDay()
	    );

	    return todayOrders.stream()
	            .map(this::convertToResponse)
	            .collect(Collectors.toList());
	}

}

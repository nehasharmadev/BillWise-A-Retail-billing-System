package com.example.Billing.System.io;

//import java.time.LocalDateTime;
import java.util.List;

//import com.example.Billing.System.io.OrderResponse.OrderItemResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {
private String customerName;
private String phoneNumber;
private List<OrderItemRequest> cartItems;
private Double subtotal;
private Double tax;
private Double grandTotal;
private PaymentMethod paymentMethod;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public static class OrderItemRequest{
	private String itemId;
	private String name;
	private Double price;
	private Integer quantity;
}

	
}

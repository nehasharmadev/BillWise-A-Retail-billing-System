package com.example.Billing.System.io;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
//@NoArgsConstructor
@Builder
public class ItemResponse {
	
    private String itemId;
	private String name;
	private BigDecimal price;
	private String categoryId;
	private String description;
	private String categoryName;
	private String imgUrl;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}

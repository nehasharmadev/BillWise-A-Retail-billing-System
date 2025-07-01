package com.example.Billing.System.io;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class CategoryResponse {

	private String categoryId;
	private String name;
	private String description;
	private String imgurl;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
	private Integer items;

}

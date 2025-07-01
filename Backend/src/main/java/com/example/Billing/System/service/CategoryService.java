package com.example.Billing.System.service;

import java.util.List;

import com.example.Billing.System.io.CategoryRequest;
import com.example.Billing.System.io.CategoryResponse;

public interface CategoryService {

	CategoryResponse add(CategoryRequest request);
	List<CategoryResponse> read();
	void delete(String categoryId);
}

package com.example.Billing.System.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.Billing.System.io.ItemRequest;
import com.example.Billing.System.io.ItemResponse;

public interface ItemService {

	ItemResponse add(ItemRequest request, MultipartFile file);
	List<ItemResponse> fetchItems();
	void deleteItem(String itemId); 
}

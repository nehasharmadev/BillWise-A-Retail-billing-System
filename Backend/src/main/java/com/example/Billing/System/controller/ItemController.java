package com.example.Billing.System.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.example.Billing.System.io.CategoryResponse;
import com.example.Billing.System.io.ItemRequest;
import com.example.Billing.System.io.ItemResponse;
import com.example.Billing.System.service.ItemService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RestController
//@RequestMapping("/admin/item")
@RequiredArgsConstructor
public class ItemController {

	private final ItemService itemService;
	
	@PostMapping("/admin/addItem")
    @ResponseStatus(HttpStatus.CREATED)
	ItemResponse addItem(@RequestPart("item") String request,
			             @RequestPart("file") MultipartFile file) {
    	ObjectMapper objectMapper = new ObjectMapper();
    	ItemRequest itemRequest = null;
    	try {
    		itemRequest = objectMapper.readValue(request, ItemRequest.class);
    		return itemService.add(itemRequest, file);
            }catch(JsonProcessingException ex) {
            	throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Error occurred while processing the json");
            }
	}
	
	@GetMapping("/getItems")
    List<ItemResponse> readItems(){
    	return itemService.fetchItems();
    }
	
	 @DeleteMapping("/admin/deleteItem/{itemId}")
	    public ResponseEntity<?> delete(@PathVariable String itemId) {
	            itemService.deleteItem(itemId);
	            return ResponseEntity.noContent().build(); // 204 No Content
	    }
	
}

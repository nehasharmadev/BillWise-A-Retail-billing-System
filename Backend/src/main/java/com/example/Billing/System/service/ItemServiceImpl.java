package com.example.Billing.System.service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.Billing.System.entity.CategoryEntity;
import com.example.Billing.System.entity.ItemEntity;
import com.example.Billing.System.exception.CategoryNotFoundException;
import com.example.Billing.System.exception.CategorySaveException;
import com.example.Billing.System.exception.ImageDeleteException;
import com.example.Billing.System.exception.ImageUploadException;
import com.example.Billing.System.exception.ItemNotFoundException;
import com.example.Billing.System.exception.ItemSaveException;
import com.example.Billing.System.io.CategoryRequest;
import com.example.Billing.System.io.CategoryResponse;
import com.example.Billing.System.io.ItemRequest;
import com.example.Billing.System.io.ItemResponse;
import com.example.Billing.System.repository.CategoryRepository;
import com.example.Billing.System.repository.ItemRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
 @RequiredArgsConstructor
public class ItemServiceImpl implements ItemService{

	private final CloudinaryImageService cloudinaryService;
	private final CategoryRepository categoryRepo;
	private final ItemRepository itemRepo;
	Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);
	@Override
	public ItemResponse add(ItemRequest request, MultipartFile file) {
		// TODO Auto-generated method stub
		Map cloudinaryResponse;
		try {
			cloudinaryResponse = cloudinaryService.upload(file);
			logger.info("image uploaded successfully for item");
		}catch(ImageUploadException ex) {
			throw new ImageUploadException(ex.getMessage());
		}
		try {
			CategoryEntity existingCategory = categoryRepo.findByCategoryId(request.getCategoryId())
		            .orElseThrow(() -> new RuntimeException("Category not found" + request.getCategoryId()));
			ItemEntity newItem = convertToEntity(request, cloudinaryResponse);
			newItem.setCategory(existingCategory);
			newItem = itemRepo.save(newItem);
			logger.info("Entity saved successfully");
	        return convertToResponse(newItem);
		}catch (Exception e) {
	        throw new ItemSaveException("Could not save Item: " + e.getMessage());
	    }
		
		
	}
private ItemResponse convertToResponse(ItemEntity newItem) {
		// TODO Auto-generated method stub
	return ItemResponse.builder()
			           .itemId(newItem.getItemId())
			           .name(newItem.getName())
			           .price(newItem.getPrice())
			           .description(newItem.getDescription())
			           .createdAt(newItem.getCreatedAt())
			           .updatedAt(newItem.getUpdatedAt())
			           .imgUrl(newItem.getImgUrl())
			           .categoryId(newItem.getCategory().getCategoryId())
			           .categoryName(newItem.getCategory().getName())
			           .build();
	
		
	}


private ItemEntity convertToEntity(ItemRequest request, Map cloudinaryResponse) {
		// TODO Auto-generated method stub
	    return ItemEntity.builder()
	    		         .itemId(UUID.randomUUID().toString())
	    		         .name(request.getName())
	    		         .description(request.getDescription())
	    		         .price(request.getPrice())
	    		         .imgUrl((String)cloudinaryResponse.get("imgurl"))
	    		         .publicId((String) cloudinaryResponse.get("publicId"))
	    		         .build();
		
	}

	@Override
	public List<ItemResponse> fetchItems() {
		// TODO Auto-generated method stub
		logger.info("Fetching all categories");
		return itemRepo.findAll().stream().map(entity -> convertToResponse(entity)).toList();

	}

	@Override
	@Transactional
	public void deleteItem(String itemId) {
		// TODO Auto-generated method stub
		logger.info("Deleting item with item id:" + itemId);
		ItemEntity entity = itemRepo.findByItemId(itemId).orElseThrow(()->new ItemNotFoundException("Item not found with itemId : " + itemId));
		logger.info("item found with itemId : " + itemId);
		try {
			 cloudinaryService.deleteImage(entity.getPublicId());
			 logger.info("Image deleted successfully");
		 }catch(ImageDeleteException ex) {
			 String errorMessage = ex.getMessage();
			 if(errorMessage != null && errorMessage.contains("not found")) {
				 logger.warn("Image not found. Procedding with deleting item");
			 }
			 else {
				 throw new ImageDeleteException(errorMessage);
			 }
		 }
		itemRepo.deleteByItemId(itemId);
	    logger.info("item deleted successfully with itemId : " + itemId); 

		
	}

}

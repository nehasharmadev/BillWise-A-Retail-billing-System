package com.example.Billing.System.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.Billing.System.entity.CategoryEntity;
import com.example.Billing.System.exception.CategoryNotFoundException;
import com.example.Billing.System.exception.CategorySaveException;
import com.example.Billing.System.exception.ImageDeleteException;
import com.example.Billing.System.exception.ImageUploadException;
import com.example.Billing.System.io.CategoryRequest;
import com.example.Billing.System.io.CategoryResponse;
import com.example.Billing.System.repository.CategoryRepository;
import com.example.Billing.System.repository.ItemRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{
    
	private final CategoryRepository categoryRepo;
	private final CloudinaryImageService cloudinaryService;
	private final ItemRepository itemRepo;
	Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);
	@Override
	public CategoryResponse add(CategoryRequest request) {
		logger.info("Started Adding new category");
		Map response;
		try {
			response = cloudinaryService.upload(request.getFile());
			logger.info("image uploaded successfully");

		}catch(ImageUploadException ex) {
			throw new ImageUploadException(ex.getMessage());
		}
		 try {
		        CategoryEntity entity = convertToEntity(request, response);
		        entity = categoryRepo.save(entity);
		        logger.info("Entity saved successfully");
		        return convertToResponse(entity);
		    } catch (Exception e) {
		        throw new CategorySaveException("Could not save category: " + e.getMessage());
		    }
	}
	
	public CategoryEntity convertToEntity(CategoryRequest request, Map response) {
		return CategoryEntity.builder()
				.categoryId(UUID.randomUUID().toString())
				.name(request.getName())
				.description(request.getDescription())
				.imgurl((String) response.get("imgurl"))
				.publicId((String) response.get("publicId"))
				.build();
	}
    
	public CategoryResponse convertToResponse(CategoryEntity entity) {
		return CategoryResponse.builder()
				.categoryId(entity.getCategoryId())
				.name(entity.getName())
				.description(entity.getDescription())
				.imgurl(entity.getImgurl())
				.createdAt(entity.getCreatedAt())
				.updatedAt(entity.getUpdatedAt())
				.items(itemRepo.countByCategoryId(entity.getId()))
				.build();
				
	}

	@Override
	public List<CategoryResponse> read() {
		logger.info("Fetching all categories");
		return categoryRepo.findAll().stream().map(entity -> convertToResponse(entity)).toList();
		
	}

		@Override
		@Transactional
		public void delete(String categoryId){
		  logger.info("Deleting category");
		  CategoryEntity entity = categoryRepo.findByCategoryId(categoryId).orElseThrow(()->new CategoryNotFoundException("Category not found with categoryId : " + categoryId));
		  logger.info("Category Found with categoryid : " + categoryId);
	      try {
			 cloudinaryService.deleteImage(entity.getPublicId());
			 logger.info("Image deleted successfully");
		 }catch(ImageDeleteException ex) {
			 String errorMessage = ex.getMessage();
			 if(errorMessage != null && errorMessage.contains("not found")) {
				 logger.warn("Image not found. Procedding with deleting category");
			 }
			 else {
				 throw new ImageDeleteException(errorMessage);
			 }
		 }
	     categoryRepo.deleteByCategoryId(categoryId);		
	     logger.info("category deleted successfully with categoryId : " + categoryId); 
		}
}

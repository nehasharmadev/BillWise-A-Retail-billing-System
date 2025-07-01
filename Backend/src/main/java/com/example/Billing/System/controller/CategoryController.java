package com.example.Billing.System.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.Billing.System.io.CategoryRequest;
import com.example.Billing.System.io.CategoryResponse;
import com.example.Billing.System.service.CategoryService;

import lombok.RequiredArgsConstructor;

//@CrossOrigin
@RestController
//@RequestMapping("/admin/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    @PostMapping("/admin/addCategory")
    @ResponseStatus(HttpStatus.CREATED)
	CategoryResponse add(@ModelAttribute CategoryRequest request) {
    	System.out.println(request);
		return categoryService.add(request);
	}
    
    @GetMapping("/getCategories")
    List<CategoryResponse> read(){
    	return categoryService.read();
    }
    @DeleteMapping("/admin/deleteCategory/{categoryId}")
    public ResponseEntity<?> delete(@PathVariable String categoryId) {
            categoryService.delete(categoryId);
            return ResponseEntity.noContent().build(); // 204 No Content
    }
    
//    @PostMapping("/upload")
//    public ResponseEntity<Map> upload(@RequestParam("image") MultipartFile file) {
//    	Map data = cloudinaryService.upload(file);
//    	return new ResponseEntity<>(data,HttpStatus.OK);
//    }
//    
//    @DeleteMapping("/deleteImg/{publicId}")
//   
//    public ResponseEntity<?> deleteImage(@PathVariable String publicId){
//    	try {
//    		cloudinaryService.deleteImage(publicId);
//    		return ResponseEntity.ok("Image deleted successfully");
//    	}catch(Exception e) {
//    		return ResponseEntity
//                    .status(HttpStatus.NOT_FOUND)
//                    .body(e);
//    	}
//    	
//    }
  }


package com.example.Billing.System.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.example.Billing.System.exception.ImageUploadException;
import com.example.Billing.System.exception.ImageDeleteException;
public interface CloudinaryImageService {

	public Map upload(MultipartFile file) ;
	public void deleteImage(String publicId);
}

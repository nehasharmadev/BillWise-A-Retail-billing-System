	package com.example.Billing.System.service;
	
	import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
	
	import org.springframework.stereotype.Service;
	import org.springframework.web.multipart.MultipartFile;
	
	import com.cloudinary.Cloudinary;
	import com.cloudinary.utils.ObjectUtils;
import com.example.Billing.System.exception.ImageDeleteException;
import com.example.Billing.System.exception.ImageUploadException;

import lombok.RequiredArgsConstructor;
	
	@Service
	@RequiredArgsConstructor
	public class CloudinaryImageServiceImpl implements CloudinaryImageService{
	
	    private final Cloudinary cloudinary;
		@Override
		public Map<String, Object> upload( MultipartFile file){

			try {
				Map data = this.cloudinary.uploader().upload(file.getBytes(), Map.of());
		    	Map<String,Object> response = new HashMap<>();
		    	response.put("imgurl", data.get( "secure_url"));
		    	response.put("publicId", data.get("public_id"));
		    	return response;	
			}catch(IOException ex) {
				throw new ImageUploadException(ex.getMessage());
			}
	    	
		}
		
	
		@Override
		public void deleteImage(String publicId){
			// TODO Auto-generated method stub
			try {
				 Map<String,Object> params = ObjectUtils.asMap("invalidate", true);	
				  Map result = cloudinary.uploader().destroy(publicId,params);
				  if (!"ok".equals(result.get("result"))) {
					  throw new ImageDeleteException("Failed to delete image: " + result.get("result"));
			        }
			}catch(IOException ex) {
				throw new ImageDeleteException(ex.getMessage());
			}
			
			
		}
	
	}

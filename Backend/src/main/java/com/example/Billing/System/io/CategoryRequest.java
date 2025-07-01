package com.example.Billing.System.io;

import org.springframework.web.multipart.MultipartFile;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class CategoryRequest {

	private String name;
	private String description;
    private MultipartFile file;
}

package com.example.Billing.System.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class CloudinaryConfig {

    @Bean
    public Cloudinary getCloudinary() {
        Map<String, String> config = ObjectUtils.asMap(
                "cloud_name", "ddyrws9lh",
                "api_key", "162848619163446",
                "api_secret", "W9Kji28pC3vRTZz5xCig2xqE0aE"
        );

        return new Cloudinary(config);
    }
    
}

package com.daniel.kshopee.config;


import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {

    @Value("${cloudinary.cloud-name}")
    private String cloudName;

    @Value("${cloudinary.api-secret}")
    private String cloudSecret;

    @Value("${cloudinary.api-key}")
    private String cloudKey;

    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary("cloudinary://" + cloudKey + ":" + cloudSecret + "@" + cloudName);
    }
}

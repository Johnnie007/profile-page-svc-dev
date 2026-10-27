package com.profile.svc.c.dc;

import com.cloudinary.Cloudinary;
import com.profile.svc.c.v.CV;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class CC {

    @Bean
    Cloudinary cloudinary(CV cv) {
        return new Cloudinary(Map.of(
                "cloud_name", cv.cloudName(),
                "api_key", cv.key(),
                "api_secret", cv.secret(),
                "secure", true
        ));
    }
}

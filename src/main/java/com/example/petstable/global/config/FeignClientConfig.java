package com.example.petstable.global.config;

import com.example.petstable.PetsTableApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackageClasses = PetsTableApplication.class)
public class FeignClientConfig {
}

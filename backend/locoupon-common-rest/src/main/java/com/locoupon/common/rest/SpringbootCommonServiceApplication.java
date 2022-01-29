package com.locoupon.common.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@EnableEurekaClient
@Configuration
@EnableAutoConfiguration
public class SpringbootCommonServiceApplication  extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootCommonServiceApplication.class, args);
	}
	
	
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SpringbootCommonServiceApplication.class);
    }

}

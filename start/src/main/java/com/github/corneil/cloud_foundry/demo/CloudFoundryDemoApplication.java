package com.github.corneil.cloud_foundry.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAutoConfiguration(exclude = {
    org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
})
public class CloudFoundryDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudFoundryDemoApplication.class, args);
	}
}

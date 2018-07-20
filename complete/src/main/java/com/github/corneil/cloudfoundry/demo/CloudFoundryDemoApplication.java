package com.github.corneil.cloudfoundry.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@EnableAutoConfiguration(exclude = {
	org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
})
public class CloudFoundryDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudFoundryDemoApplication.class, args);
	}
}

package com.rpa.demo;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author abhimanyu.thite
 * Configuration file for spring
 */
@EnableConfigurationProperties
@Configuration
public class Config {
	
	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}

}

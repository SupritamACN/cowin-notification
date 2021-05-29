package com.help.cowin;

import com.help.cowin.util.ErrorResponseHandler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableAsync
@EnableScheduling
public class CowinApplication {
	
	
	public static void main(String[] args) {
		SpringApplication.run(CowinApplication.class, args);
    }
    
    @Bean
    public RestTemplate getRestTemplate() {
      RestTemplate restTemplate = new RestTemplate();
      restTemplate.setErrorHandler(new ErrorResponseHandler());
      return restTemplate;
   }
}

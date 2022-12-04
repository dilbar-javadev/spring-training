package com.cydeo;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication  // also includes @Configuration
@EnableFeignClients    // should be put on top of configuration class
public class Spring17RestConsumingApisApplication {

    public static void main(String[] args) {

        SpringApplication.run(Spring17RestConsumingApisApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
    // we need this Bean is because we need the methods of this restTemplate in our controllers or service layer
    // we need his bean so we can inject and use its method

    @Bean
    public ModelMapper mapper() {
        return new ModelMapper();
    }

}

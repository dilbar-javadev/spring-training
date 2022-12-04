package com.cydeo.client;

import com.cydeo.dto.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(url = "https://jsonplaceholder.typicode.com",name="USER-CLIENT")
public interface UserClient {

    @GetMapping("/users")
    List<User> getUsers();  // when we call this method somewhere, it will go to above URL and this endpoint and give back response in List of User format

}

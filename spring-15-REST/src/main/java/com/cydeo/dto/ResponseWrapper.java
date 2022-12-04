package com.cydeo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
public class ResponseWrapper {
    // this is a class for us to change how our data(response) looks like in the API
    // depends on the business logic
    // it means we want to add the following information in our responses
    // it is also a DTO
    private boolean success;
    private String message;
    private Integer code;
    private Object data;

    public ResponseWrapper(String message,Object data){
        this.message = message;
        this.data=data;
        this.code= HttpStatus.OK.value();
        this.success=true;
    }

    public ResponseWrapper(String message){
        this.message=message;
        this.code=HttpStatus.OK.value();
        this.success=true;
    }

}

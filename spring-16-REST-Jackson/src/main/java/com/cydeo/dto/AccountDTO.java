package com.cydeo.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@JsonIgnoreProperties(value = {"address", "country"}, ignoreUnknown = true)
@JsonIgnoreProperties(ignoreUnknown = true)  // if in request some field is unknown and not needed
public class AccountDTO {

    @JsonIgnore  // this field will be ignored in both requestBody and ResponseBody
    private String name;
    private String address;
    private String country;
    private String state;
    private String city;
    private Integer age;
    private String postalCode;

    @JsonBackReference  // This field is not going to be serialized -- means in Json reference we will not see this field
    private UserDTO user;

}

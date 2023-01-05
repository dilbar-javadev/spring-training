package com.cydeo.controller;

import com.cydeo.dto.StudentDTO;
import com.cydeo.service.StudentService;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;   // are providing methods that can be used in andExpect() ... status(), content() ...

@WebMvcTest(StudentController.class)
public class StudentControllerTest {

    @Autowired
    private MockMvc mvc;   // we need a bean to make MVC part work

    @MockBean  // in Mockito we used @Mock  -> @Mock cannot mock interfaces directly, it is not working with bean but working with objects directly
               // @MockBean is working with bean and applicationContext itself -> it is able to find the bean that I'm creating from studentService
    StudentService studentService;  // why we need? -> studentController bean has a dependency to studentService bean
                                    // even though the getStudent method is not using the service, the class itself is depending on it.
                                    // in Mockito we didn't need to add all the beans because in Mockito we don't use applicationContext, app itself is not running

    @Test
    void getStudent_Test() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/student")   //  |
                .accept(MediaType.APPLICATION_JSON))                   //  _|   these two lines we are making request
                .andExpect(status().isOk())                                                                                     //  |
                .andExpect(content().json("{\"firstName\": \"Mike\", \"lastName\":  \"Smith\", \"age\":  20}"))      //  _|   these two lines we are making verification
                .andDo(print())   // to print result in my console -- coming from MockMvcResultHandlers class                     |   we are printing result/response/request
                .andReturn();                                                                                                //  _|   we can return the response
    }

    @Test
    void jsonAssert_Test() throws Exception {

        String expected = "{\"firstName\": \"Mike\", \"lastName\":  \"Smith\"}";
        String actual = mvc.perform(MockMvcRequestBuilders.get("/student")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        JSONAssert.assertEquals(expected, actual, false);  // the "strict" field will judge if the json is exactly same or partially same
    }

    @Test
    void getStudents_Test() throws Exception {

        when(studentService.getStudents()).thenReturn(Arrays.asList(
                new StudentDTO("John", "Doe", 20),
                new StudentDTO("Tom", "Hanks", 50)
        ));  // creating my stub (behavior for my mock object / StudentService obj)

        mvc.perform(MockMvcRequestBuilders.get("/students")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(content()
                        .json("[{\"firstName\": \"John\", \"lastName\":  \"Doe\", \"age\":  20}" +
                                ", {\"firstName\": \"Tom\", \"lastName\":  \"Hanks\", \"age\":  50}]"))
                .andDo(print())
                .andReturn();
    }

}

package com.kw.nodeimageeditorbackend.integration.controlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kw.nodeimageeditorbackend.user.requests.AuthenticationRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class HelloTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @Test
    void sayHello() throws Exception {
        mockMvc.perform(get("/1"))
            .andExpect(status().isOk());
    }

    @Test
    void sayHelloAuth() throws Exception {
        var authBody = new AuthenticationRequest("alex", "password");
        var response = mockMvc.perform(post("/api/login")
                .contentType("application/json")
                .content(mapper.writeValueAsString(authBody)))
                .andExpect(status().isOk())
                .andReturn();
        var authToken = response.getResponse().getHeader("Authorization");
        mockMvc.perform(get("/2")
                .header("Authorization", authToken))
                .andExpect(status().isOk());
    }
}
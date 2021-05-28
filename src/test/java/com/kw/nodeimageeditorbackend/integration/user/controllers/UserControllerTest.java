package com.kw.nodeimageeditorbackend.integration.user.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kw.nodeimageeditorbackend.user.requests.AuthenticationRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class serControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;

    @Test
    void should_return_406_to_login_request_containing_incorrect_credentials() throws Exception {
        var authBody = new AuthenticationRequest("alex", "wrong_password");
        mockMvc.perform(
                post("/api/login")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(authBody)))
                .andExpect(status().is(406));
    }

    @Test
    void should_return_200_and_tokens_when_credentials_are_correct() throws Exception {
        var authBody = new AuthenticationRequest("alex", "password");
        var response = mockMvc.perform(
                post("/api/login")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(authBody)))
                .andExpect(status().is(200))
                .andReturn().getResponse();

        assertNotNull(response.getHeader("Authorization"));
        assertNotNull(response.getCookie("token"));
    }

    @Test
    void should_return_200_when_refresh_token_is_correct() throws Exception {
        var authBody = new AuthenticationRequest("alex", "password");
        var authResponse = mockMvc.perform(
                post("/api/login")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(authBody)))
                .andExpect(status().is(200))
                .andReturn().getResponse();

        assert mockMvc.perform(
                get("/api/token-refresh")
                        .cookie(authResponse.getCookie("token")))
                .andExpect(status().isOk())
                .andReturn().getResponse().getHeader("Authorization") != null;

    }

    @Test
    void createUser() {
    }

    @Test
    void deleteUser() {
    }

    @Test
    void patchUser() {
    }
}
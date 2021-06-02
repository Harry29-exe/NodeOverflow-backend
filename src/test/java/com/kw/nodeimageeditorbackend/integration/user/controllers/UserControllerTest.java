package com.kw.nodeimageeditorbackend.integration.user.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kw.nodeimageeditorbackend.user.requests.AuthenticationRequest;
import com.kw.nodeimageeditorbackend.user.requests.CreateUserRequest;
import com.kw.nodeimageeditorbackend.user.requests.DeleteUserRequest;
import org.aspectj.lang.annotation.Before;
import org.hibernate.TransactionException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.transaction.TransactionalException;

import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationControllerTest {

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


}

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserCreateTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;

    @Test
    @Rollback
    void should_create_new_user() throws Exception {
        var request = new CreateUserRequest("newUser", "newUser@user", "123");

        mockMvc.perform(
                post("/api/register")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().is(201));

        var authBody = new AuthenticationRequest("newUser", "123");
        mockMvc.perform(
                post("/api/login")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(authBody)))
                .andExpect(status().is(200));
    }

    @Test
    @Rollback
    void should_throw_Exception() throws Exception {
        var request = new CreateUserRequest("alex", "alex@alex", "password");

        mockMvc.perform(
                post("/api/register")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(request)))
                .andExpect(status().is4xxClientError());
    }
}

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserDeleteTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;


    @Test
    @Rollback
    void should_throw_AuthorizationException() throws Exception {
        var createUserRequest = new CreateUserRequest("newUser", "newUser@user", "123");

        mockMvc.perform(
                post("/api/register")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(createUserRequest)))
                .andExpect(status().is(201));

        var authBody = new AuthenticationRequest("newUser", "123");
        var response = mockMvc.perform(
                post("/api/login")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(authBody)))
                .andExpect(status().is(200))
                .andReturn().getResponse();
        var authToken = response.getCookie("token").getValue();

        var deleteUserRequest = new DeleteUserRequest("newUser", "123");
        mockMvc.perform(
                delete("/api/user")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(deleteUserRequest))
                        .cookie(new Cookie("Authorization", authToken)))
                .andExpect(status().is(403));
    }

    @Test
    @Rollback
    void deleteUser() throws Exception {
        var createUserRequest = new CreateUserRequest("newUser", "newUser@user", "123");

        mockMvc.perform(
                post("/api/register")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(createUserRequest)))
                .andExpect(status().is(201));

        var authBody = new AuthenticationRequest("newUser", "123");
        var response = mockMvc.perform(
                post("/api/login")
                        .contentType("application/json")
                        .content(mapper.writeValueAsString(authBody)))
                .andExpect(status().is(200))
                .andReturn().getResponse();
        var authToken = response.getHeader("Authorization");

        var deleteUserRequest = new DeleteUserRequest("newUser", "123");
        mockMvc.perform(
                delete("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(deleteUserRequest))
                        .header("Authorization", authToken))
                .andExpect(status().isOk());
    }
}
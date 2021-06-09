package com.kw.nodeimageeditorbackend.integration.controlers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/")
@CrossOrigin
public class Hello {

    @Value("${frontend.address}")
    private String address;

    @GetMapping("1")
    public String sayHello(HttpServletResponse response) {
        response.addHeader("Hello", "Hello");
        System.out.println(address);
        return "Hello1";
    }

    @GetMapping("2")
    public String sayHelloAuth() {
        return "Authorized Hello Word";
    }

}

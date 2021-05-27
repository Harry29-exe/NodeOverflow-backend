package com.kw.nodeimageeditorbackend.integration.controlers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/")
@CrossOrigin
public class Hello {

    @GetMapping("1")
    public String sayHello(HttpServletResponse response) {
        response.addHeader("Hello", "Hello");
        return "Hello1";
    }

    @GetMapping("2")
    public String sayHelloAuth() {
        return "Authorized Hello Word";
    }

}
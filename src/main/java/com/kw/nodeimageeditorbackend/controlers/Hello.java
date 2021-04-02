package com.kw.nodeimageeditorbackend.controlers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class Hello {

    @GetMapping("1")
    public String sayHello() {
        return "Hello World";
    }

    @GetMapping("2")
    public String sayHelloAuth() {
        return "Authorized Hello Word";
    }
}

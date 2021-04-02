package com.kw.nodeimageeditorbackend.controlers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:5500")
@RequestMapping("/api/")
public class UserController {

    @CrossOrigin(origins = "http://localhost:5500")
    @PostMapping("login")
    public void login() {
    }


}

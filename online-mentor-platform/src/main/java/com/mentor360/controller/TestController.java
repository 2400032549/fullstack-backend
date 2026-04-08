package com.mentor360.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/testing/protected")
    public String protectedEndpoint(Authentication authentication) {
        return "Access granted to: " + authentication.getName();
    }
}

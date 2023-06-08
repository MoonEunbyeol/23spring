package com.example.ch16.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("")
    public String getTest() {
        return "Get Test";
    }

    @PostMapping("/user")
    @PreAuthorize("hasRole('ROLE_USER')") // 메소드에 한해서 권한 지정
    public String getUserTest() {
        return "Get User Test";
    }

    @PostMapping("/admin") // admin 권한이 있는 사람만 post 가능
    public String adminTest() { // 권한 테스트용
        return "Admin Test";
    }
}

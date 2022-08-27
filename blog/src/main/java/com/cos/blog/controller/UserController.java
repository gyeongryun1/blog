package com.cos.blog.controller;

import com.cos.blog.model.User;
import com.cos.blog.security.UserPrincipalService;
import com.cos.blog.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/user/joinForm")
    public String joinForm() {

        return "/user/joinForm";
    }
    @GetMapping("user/loginForm")
    public String loginForm() {

        return "/user/loginForm";
    }

    @PostMapping("/user/join")
    public String join(@ModelAttribute User user) {
        log.info("***유저정보={}", user);
        userService.join(user);

        return "/index";
    }
    @PostMapping("/user/login")
    public String login(@ModelAttribute User user) {
        log.info("***유저정보={}", user);


        return "/index";
    }
    @GetMapping("/info")
    public String Login() {

        return "/info";
    }
    @GetMapping("/loginFail")
    public String loginFail() {

        return "/loginFail";
    }


}

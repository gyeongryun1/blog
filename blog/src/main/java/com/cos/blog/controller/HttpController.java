package com.cos.blog.controller;

import com.cos.blog.member.Member;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class HttpController {

    @GetMapping("/get")
    public String getTest(@ModelAttribute Member m) {
        return "get 요청"+ m.getId() + m.getUsername()+m.getPassword()+m.getEmail();
    }

    @PostMapping("/post")
    public String postTest(@ModelAttribute Member m) {
        return "post 요청"+ m.getId() + m.getUsername()+m.getPassword()+m.getEmail();
    }

    @PutMapping("/put")
    public String putTest() {
        return "get 요청";
    }

    @DeleteMapping("/delete")
    public String deleteTest() {
        return "delete 요청";
    }

}

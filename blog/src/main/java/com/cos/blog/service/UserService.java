package com.cos.blog.service;

import com.cos.blog.Repository.UserRepository;
import com.cos.blog.model.User;
import com.cos.blog.security.UserPrincipalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository repository;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    public int join(User user) {

        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            log.info("////////////"+user.getPassword());
            repository.save(user);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            log.info("UserService 회원가입 {}",e.getMessage());
            return -1;
        }

    }
    }




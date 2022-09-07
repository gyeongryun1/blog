package com.cos.blog.service;

import com.cos.blog.Repository.UserRepository;
import com.cos.blog.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository repository;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;
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

    @Transactional
    public void 회원정보수정(User user, Long id) {
       User findUser = repository.findById(id).orElseThrow(()->{return new IllegalArgumentException("회원정보수정 실패"); } ) ;
        String rawPassword = user.getPassword();
        String encPassword = passwordEncoder.encode(rawPassword);
       findUser.setEmail(user.getEmail());
       findUser.setPassword(encPassword);

       repository.flush();
//        //세션 등록
        Authentication authentication =authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

    }
}




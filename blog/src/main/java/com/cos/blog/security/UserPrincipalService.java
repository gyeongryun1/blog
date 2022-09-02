package com.cos.blog.security;

import com.cos.blog.Repository.UserRepository;
import com.cos.blog.model.PrincipalDetail;
import com.cos.blog.model.User;
import com.cos.blog.model.UserAuth;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserPrincipalService implements UserDetailsService {

    private final UserRepository repository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User principal = repository.findByUsername(username);
        if (principal == null) {
            throw new UsernameNotFoundException(username);
        }
        return new PrincipalDetail(principal);
//                org.springframework.security.core.userdetails.
//                User.builder()
//                .username(user.getUsername())
//                .password(user.getPassword())
//                .roles("Role_"+user.getRole())
//                .build();
    }



}

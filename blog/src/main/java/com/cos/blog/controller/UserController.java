package com.cos.blog.controller;

import com.cos.blog.model.OauthToken;
import com.cos.blog.model.PrincipalDetail;
import com.cos.blog.model.User;
import com.cos.blog.security.UserPrincipalService;
import com.cos.blog.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @ResponseBody
    @GetMapping("/auth/kakao/callback")
    public String kakaoCallBack(String code) {
        /** Post 방식으로 ket=value를 요청
         *  Retrofit2
         *  OkHttp
         *  RestTemplate
         */
        RestTemplate rt = new RestTemplate();
        //header 오브젝트 생성
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type","application/x-www-form-urlencoded;charset=utf-8");

        //httpbody 오브젝트 생성
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "34dc765c12bd0dfe35c15917ee571b87");
        params.add("redirect_uri", "http://localhost:8080/auth/kakao/callback");
        params.add("code", code);

        //httpheader와 httpbody를 하나에 담기
        HttpEntity<MultiValueMap<String,String>> kakaoTotkenRequest = new HttpEntity<>(params,httpHeaders);
        //http 요청하기 -post 방식으로
        ResponseEntity<String> response= rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTotkenRequest,
                String.class
        );
        // Gson, Json Simple, ObjectMapper -> json to object
        ObjectMapper objectMapper = new ObjectMapper();
        OauthToken oauthToken = null;
        try {
            oauthToken = objectMapper.readValue(response.getBody(), OauthToken.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        System.out.println("oauthToken = " + oauthToken.getAccess_token());
        return response.getBody();
    }

    @PostMapping("/user/update")
    public RedirectView updateUser(@ModelAttribute User user, @AuthenticationPrincipal PrincipalDetail principal) {
        userService.회원정보수정(user,principal.getUser().getId());


        return new RedirectView("/");
    }
    @GetMapping("/user/updateUserForm")
    public String updateUserForm(Model model, @AuthenticationPrincipal PrincipalDetail principal) {
        model.addAttribute("principal", principal);
        return "/user/updateUser";
    }

    @GetMapping("/user/joinForm")
    public String joinForm() {

        return "/user/joinForm";
    }
    @GetMapping("user/loginForm")
    public String loginForm() {

        return "/user/loginForm";
    }

    @PostMapping("/user/join")
    public RedirectView join(@ModelAttribute User user) {
        log.info("***유저정보={}", user);
        userService.join(user);

        return new RedirectView("/");
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

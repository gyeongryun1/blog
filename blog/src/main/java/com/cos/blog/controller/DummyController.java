package com.cos.blog.controller;

import com.cos.blog.Repository.UserRepository;
import com.cos.blog.model.User;
import com.cos.blog.model.UserAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@RestController
public class DummyController {

    @Autowired
    private UserRepository repository;

    //save 함수는 id가 없으면 insert, 있으면 update

    @DeleteMapping("/dummy/user/{id}")
    public String delete(@PathVariable Long id) {

        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            return  "삭제 실패. 해당 id는 데이터베이스에 없음";
        }


        return "삭제완료 id:" +id;
    }

    @Transactional
    @PutMapping("/dummy/user/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User requestUser) { //메세지 컨버터의 잭슨 라이브러리가 Json -> 자바오브젝트

        User findUser = repository.findById(id).orElseThrow(()->{
        return new IllegalArgumentException("수정에 실패하였습니다");});

        findUser.setPassword(requestUser.getPassword());
        findUser.setEmail(requestUser.getEmail());

//        repository.save(findUser);
        /** 트랜잭셔널로 인한 더티체킹 발생. 변화를 감지하고 있다가 트랜잭션 종료직전에 쿼리 발생   */
        return findUser;
    }

    //한 페이지당 2건의 데이터를 받는다
    @GetMapping("/dummy/users/page")
    public List<User> pageList(@PageableDefault(size = 2,sort = "id",direction = Sort.Direction.DESC)Pageable pageable) {
        Page<User> users = repository.findAll(pageable);
        List paginUsers = users.getContent();
        return paginUsers;
    }
//    public Page<User> pageList(@PageableDefault(size = 2,sort = "id",direction = Sort.Direction.DESC)Pageable pageable) {
//        Page<User> users = repository.findAll(pageable);
//        return users;
//    }

    @GetMapping("/dummy/users")
    public List<User> list() {

        List<User> allUser = repository.findAll();
        return allUser;
    }

    @PostMapping("/dummy/join")
    public String join(User user) {
        System.out.println(user.toString());
        user.setRole(UserAuth.USER);
        repository.save(user);
        return "Complete";
    }
    @GetMapping("/dummy/user/{id}")
    public User detail(@PathVariable Long id) throws Throwable {
        User finduser = repository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("해당 사용자가 없습니다");
        });
        // 요청 : 웹브라우저
        // user객체: 자바 오브젝트
        // 변환(웹브라우저가 이해할 수 있는 데이터) : Json
        // 스프링부트 : MessageConverter가 응답시 자동작동(자바 오브젝트를 리턴하면
        // jackson이라는 라이브러리를 호출해서 Json으로 변환해준다)
        return finduser;
    }


//    @GetMapping("/dummy/user/{id}")
//    public User detail(@PathVariable Long id) throws Throwable {
//    User finduser = repository.findById(id).orElseThrow(new Supplier<Throwable>() {
//        @Override
//        public Throwable get() {
//            return new IllegalArgumentException("해당 유저는 없음 Id: " +id);
//        }
////    User finduser = repository.findById(id).orElseThrow(new Supplier<Throwable>() {
////        @Override
////        public Throwable get() {
////            return new IllegalArgumentException("해당 유저는 없음 Id: " +id);
////        }
//    }); return finduser;
//    }


    }
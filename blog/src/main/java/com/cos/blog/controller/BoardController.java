package com.cos.blog.controller;

import com.cos.blog.Repository.UserRepository;
import com.cos.blog.model.Board;
import com.cos.blog.model.User;
import com.cos.blog.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
public class BoardController {

    private final BoardService boardService;
    private final UserRepository userRepository;

    @GetMapping("/")
    public String index(Model model, @PageableDefault(size=3,sort="id",direction = Sort.Direction.DESC)Pageable pageable) {
        Page<Board> boards = boardService.boards(pageable);
        model.addAttribute("boards", boardService.boards(pageable));
        return "/index";

    }


    @GetMapping("/board/saveForm")
    public String saveForm() {

        return "/board/saveForm";
    }

    /** 여기 중복 없앨 수 없나? */
    @PostMapping("board/create")
    public String boardCreate(@ModelAttribute Board board,Principal principal, Model model,@PageableDefault(size=3,sort="id",direction = Sort.Direction.DESC)Pageable pageable ) {
        User byUsername = userRepository.findByUsername(principal.getName());
        boardService.BoardCreate(board,byUsername);
        System.out.println("===== 여기까지 정상작동함 ======");
        Page<Board> boards = boardService.boards(pageable);
        model.addAttribute("boards", boardService.boards(pageable));

        return "/index";
    }



}

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
        boards.
        model.addAttribute("boards", boardService.boards(pageable));
        return "/index";
        pageable
    }


    @GetMapping("/board/saveForm")
    public String saveForm() {

        return "/board/saveForm";
    }

    @PostMapping("board/create")
    public String boardCreate(@ModelAttribute Board board,Principal principal) {
        User byUsername = userRepository.findByUsername(principal.getName());
        boardService.BoardCreate(board,byUsername);
        return "/index";
    }



}

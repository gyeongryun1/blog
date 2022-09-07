package com.cos.blog.controller;

import com.cos.blog.Repository.UserRepository;
import com.cos.blog.model.Board;
import com.cos.blog.model.PrincipalDetail;
import com.cos.blog.model.User;
import com.cos.blog.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

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

    @Transactional
    @PostMapping("board/update")
    public RedirectView update(@ModelAttribute Board board) {
        System.out.println(board.getId());
        boardService.글수정하기(board);
        return new RedirectView("/updateForm/board/"+board.getId());
    }
    @PostMapping("board/create")
    public RedirectView boardCreate(@ModelAttribute Board board, Principal principal) {
        User byUsername = userRepository.findByUsername(principal.getName());
        boardService.BoardCreate(board,byUsername);
        return new RedirectView("/");}
    @GetMapping("/updateForm/board/{id}")
    public String updateForm(@PathVariable Long id,Model model) {
        model.addAttribute("board", boardService.글상세보기(id));
        return "/board/updateForm";
    }
    @GetMapping("/delete/board/{id}")
    public RedirectView deleteById(@PathVariable Long id) {
        boardService.글삭제하기(id);
        return new RedirectView("/");
    }
    @GetMapping("/board/{id}")
    public String findById(@PathVariable Long id, Model model,
                           @AuthenticationPrincipal PrincipalDetail principal) {
        Board findUser = boardService.글상세보기(id);
        model.addAttribute("board", findUser);
        model.addAttribute("principal", principal);

        return "/board/detail";
    }

    @GetMapping("/board/saveForm")
    public String saveForm() {

        return "/board/saveForm";

    }

    /** 여기 중복 없앨 수 없나? */


}

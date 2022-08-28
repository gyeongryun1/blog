package com.cos.blog.service;

import com.cos.blog.Repository.BoardRepository;
import com.cos.blog.model.Board;
import com.cos.blog.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class BoardService {

    @Autowired
    private BoardRepository repository;

    public void BoardCreate(Board board, User user) {
        board.setCount(0);
        board.setUser(user);
        repository.save(board);
    }

    public Page<Board> boards(Pageable pageable) {
        return repository.findAll(pageable);
    }
}




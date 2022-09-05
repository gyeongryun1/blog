package com.cos.blog.service;

import com.cos.blog.Repository.BoardRepository;
import com.cos.blog.model.Board;
import com.cos.blog.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class BoardService {

    @Autowired
    private BoardRepository repository;

    @Transactional
    public void BoardCreate(Board board, User user) {
        board.setCount(0);
        board.setUser(user);
        repository.save(board);
    }

    @Transactional(readOnly = true)
    public Page<Board> boards(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Transactional(readOnly = true)

    public Board 글상세보기(Long id) {
        return repository.findById(id)
                .orElseThrow(()->{return new IllegalArgumentException("글 상세보기 실패: 아이디를 찾을 수 없습니다");});
    }
    @Transactional
    public void 글삭제하기(Long id) {
        repository.deleteById(id);
    }

    @Transactional
    public void 글수정하기(Board board) {
      Optional<Board> target = repository.findById(board.getId());
      if(target==null){
          log.info("글 수정하기 실패: 해당 아이디를 찾을 수 없습니다");
      }
        target.get().setTitle(board.getTitle());
        target.get().setContent(board.getContent());
        log.info("title={}  content={}",target.get().getTitle(),target.get().getTitle());
    }
}




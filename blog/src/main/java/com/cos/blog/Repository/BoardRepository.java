package com.cos.blog.Repository;

import com.cos.blog.model.Board;
import com.cos.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board,Long> {
}

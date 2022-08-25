package com.cos.blog.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder @Entity
public class Reply {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 200) // 대용량 데이터
    private String content;

    @ManyToOne
    @JoinColumn(name="boardId")
    private Board board;

    @ManyToOne
    @JoinColumn(name="userId")
    private User user;

    @CreationTimestamp
    private Timestamp createDate;

}

package com.cos.blog.model;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder @Entity
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String username;
    @Column(nullable = false, length = 100)
    private String password;
    @Column(nullable = false, length = 50)
    private String email;
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'USER'") //"'USER'"
    private UserAuth role;
    @CreationTimestamp //시간 자동 입력
    private Timestamp createDate;

}

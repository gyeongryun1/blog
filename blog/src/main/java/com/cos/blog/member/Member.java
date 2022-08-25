package com.cos.blog.member;

import lombok.*;

@Getter  @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class Member {

    private Long id;
    private String username;
    private String password;
    private String email;

}

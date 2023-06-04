package com.example.spring_board.author.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Column(length = 50)
    private String name;

    @Setter
    @Column(length = 50, unique = true)
    private String email;

    @Setter
    @Column(length = 20)
    private String password;

    @Column(length=10)
    private String role;

    //mysql에서는 datetime 형식으로 컬럼 생성
    @Column
    private LocalDateTime createDate;

    //    생성자방식과 builder패턴
    @Builder
    public Author(String name, String email, String password, String role){
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.createDate = LocalDateTime.now();
    }

}

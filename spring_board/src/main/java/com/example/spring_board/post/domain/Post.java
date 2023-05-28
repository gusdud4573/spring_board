package com.example.spring_board.post.domain;

import com.example.spring_board.author.domain.Author;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50)
    private String title;
    @Column
    private String contents;

    //author 테이블 id를 fk로 잡는다.
    //author_id의 type이 Author가 됨에 유의
    //author_id는 author 테이블의 id에 fk가 걸려있음을 Author 객체 타입을 지정함으로써 매핑시켰다
    //그리고 Author author 여기서 author 라는 변수명은 테이블의 컬럼명이 아니라,
    //추후에 post 테이블에서 author를 조회할 때 변수명으로 사용되고, 그 관계성을 ManyToOne으로 표현
    //Author 테이블 입장에서는 1:N의 관계이고, post 테이블 입장에선 한 사람이 여러개의 글을 쓸 수 있어서 N:1이다.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name="author_id")
    private Author author;

    @Column()
    private LocalDateTime createDate;

}

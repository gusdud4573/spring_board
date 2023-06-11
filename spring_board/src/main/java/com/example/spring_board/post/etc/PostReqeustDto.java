package com.example.spring_board.post.etc;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
//내부적으로 Setter사용해서 html의 input값을 꺼내어 담으므로, setter가 필요
@Setter
public class PostReqeustDto {
    private String id;
    private String title;
    private String contents;
    private String email;
    private String appointment;
    private String appointment_time;
}
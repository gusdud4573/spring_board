package com.example.spring_board;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@EnableRedisHttpSession	//redis에 세션을 저장할 경우에 main 프로그램에 EnableRedisHttpSession 어노테이션 필요
// 세션만 있는게 아니라, token이라는 로그인을 위한 객체도 많이 사용된다.
@EnableScheduling
@SpringBootApplication
public class SpringBoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBoardApplication.class, args);
	}

}

package com.example.spring_board.post.service;

import com.example.spring_board.post.domain.Post;
import com.example.spring_board.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class PostScheduler {

/*
  스케줄러는 초분시일월주 등 시간 옵션을 줘야 몇분에 한번 몇시간에 한번 등 시간을 주기로 돌아간다.
  일반적으로 cron이란 리눅스/스프링/그외 각종 프레임워크에서 스케줄러를 의미한다.
  스케줄러(cron) : 너무 빈번하면 서버에 부하, 실무에서는 너무 빈번하지 않게 스케줄러 조절 및 야간에 일반적으로 작업을 한다. - 데이터 통계
*/
/*
  배치(batch) : 수행해야 할 작업이 많고, 각각의 작업이 연속적으로 실행되어야 할때 사용하는 서비스 -> DB의 트랜잭션과 유사한 의미
     트랜잭션? 쿼리를 묶어서 실행하는것. 트랜잭션에는 commit(확정), rollback(취소) 개념이 있음
     배치는 전체 job을 한 트랜잭션으로 묶을때 많이 사용
*/
    @Autowired
    private PostRepository postRepository;

    @Scheduled(cron = "0 0/1 * * * *")
    public void postSchedule(){
        List<Post> posts =  postRepository.findByAppointment("Y");
        for(Post post : posts) {
            LocalDateTime currentTime = LocalDateTime.now();
            if(currentTime.isAfter(post.getAppointment_time())==true){
                post.setAppointment(null);
                postRepository.save(post);
            }
        }
    }

}

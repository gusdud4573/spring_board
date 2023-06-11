package com.example.spring_board.post.service;


import com.example.spring_board.author.domain.Author;
import com.example.spring_board.author.service.AuthorService;
import com.example.spring_board.post.domain.Post;
import com.example.spring_board.post.etc.PostReqeustDto;
import com.example.spring_board.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Transactional
public class PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private AuthorService authorService;

    public void create(PostReqeustDto postReqeustDto) throws SQLException {
        String my_appointment = null;
        LocalDateTime my_appointment_time = null;
        if(postReqeustDto.getAppointment()!=null&&!postReqeustDto.getAppointment_time().isEmpty()){
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            LocalDateTime localDateTime = LocalDateTime.parse(postReqeustDto.getAppointment_time(), dateTimeFormatter);
            LocalDateTime currentTime = LocalDateTime.now();
            if(currentTime.isBefore(localDateTime)==true){
                my_appointment = "Y";
                my_appointment_time = localDateTime;
            }
        }
        Author author1 = authorService.findByEmail(postReqeustDto.getEmail());
        Post post1 = Post.builder()
                .title(postReqeustDto.getTitle())
                .contents(postReqeustDto.getContents())
//                post에는 author변수가 있으므로, post생성시 author객체를 넘겨주면,
//                내부적으로 author객체에서 author_id를 꺼내어 DB에 넣게 된다.
                .author(author1)
                .appointment(my_appointment)
                .appointment_time(my_appointment_time)
                .build();

        postRepository.save(post1);
    }

    public List<Post> findAll(){
        List<Post> posts =  postRepository.findByAppointment(null);
        return posts;
    }

    public Post findById(Long myId) throws EntityNotFoundException {
        Post post =  postRepository.findById(myId).orElseThrow(EntityNotFoundException::new);
        return post;
    }

    public void update(PostReqeustDto postReqeustDto) throws Exception {
        Post post1 = postRepository.findById(Long.parseLong(postReqeustDto.getId())).orElseThrow(Exception::new);
        if(post1 == null){
            throw new Exception();
        }else{
            post1.setTitle(postReqeustDto.getTitle());
            post1.setContents(postReqeustDto.getEmail());
            postRepository.save(post1);
        }
    }

    public void delete(Long id) {
        postRepository.delete(this.findById(id));
    }
}
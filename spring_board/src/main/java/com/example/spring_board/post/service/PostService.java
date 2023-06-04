package com.example.spring_board.post.service;


import com.example.spring_board.post.domain.Post;
import com.example.spring_board.post.etc.PostReqeustDto;
import com.example.spring_board.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.sql.SQLException;
import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public void create(Post post) throws SQLException {
        postRepository.save(post);
    }

    public List<Post> findAll(){
        List<Post> posts =  postRepository.findAll();
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
package com.example.spring_board.post.controller;


import com.example.spring_board.author.domain.Author;
import com.example.spring_board.author.service.AuthorService;
import com.example.spring_board.post.domain.Post;
import com.example.spring_board.post.etc.PostReqeustDto;
import com.example.spring_board.post.service.PostService;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.EntityNotFoundException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class PostController {

    @Autowired
    private PostService postService;


    @GetMapping("posts/new")
    public String postCreateForm(){
        return "post/post-register";
    }

    @PostMapping("posts/new")
    public String postCreate(PostReqeustDto postReqeustDto) throws SQLException {
        postService.create(postReqeustDto);
        return "redirect:/";
    }

    @GetMapping("posts")
    public String postFindAll(Model model){
        List<Post> posts = postService.findAll();
        model.addAttribute("postList", posts);
        return "post/post-list";
    }

    @GetMapping("post")
    public String postFindById(@RequestParam(value = "id")Long myId, Model model) throws EntityNotFoundException {
        Post post = postService.findById(myId);
        model.addAttribute("post", post);
        return "post/post-detail";
    }

    @PostMapping("post/update")
    public String postUpdate(PostReqeustDto postReqeustDto) throws Exception {
        postService.update(postReqeustDto);
        return "redirect:/";
    }

    @GetMapping("post/delete")
    public String deletePost(@RequestParam(value = "id")String id){
        postService.delete(Long.parseLong(id));
        return "redirect:/";
    }


}
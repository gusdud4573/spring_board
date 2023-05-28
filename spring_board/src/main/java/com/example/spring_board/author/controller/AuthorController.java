package com.example.spring_board.author.controller;

import com.example.spring_board.author.domain.Author;
import com.example.spring_board.author.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class AuthorController {
    @GetMapping("/")
    public String authorHome(){
        return "home";
    }
    @Autowired
    private AuthorService authorService;

    @GetMapping("authors/new")
    public String authorCreateForm(){
        return "author/author-register";
    }

    @PostMapping("authors/new")
    public String authorCreate(@RequestParam(value="name")String myname,
                               @RequestParam(value="email")String myemail,
                               @RequestParam(value="password")String mypassword) throws SQLException {
        Author author1 = new Author();
        author1.setName(myname);
        author1.setEmail(myemail);
        author1.setPassword(mypassword);
        author1.setRole("user");
        author1.setCreateDate(LocalDateTime.now()); //현재 시각을 찍는 메서드
        authorService.create(author1);
        return "redirect:/";
    }

    @GetMapping("authors")
    public String authorsFindAll(Model model) throws SQLException {
        List<Author> authors = authorService.findAll();
        model.addAttribute("authorList", authors);
        return "author/author-list";
    }

    @GetMapping("author")
    public String authorDetail(@RequestParam(value = "id")Long myId, Model model) throws SQLException {
        Author author = authorService.findById(myId);
        model.addAttribute("author", author);
        return "author/author-detail";
    }

    @PostMapping("author/update")
    public String authorUpdate(@RequestParam(value="id")String myId,
                               @RequestParam(value="name")String myname,
                               @RequestParam(value="email")String myemail,
                               @RequestParam(value="password")String mypassword) throws Exception {
        Author author1 = new Author();
        author1.setId(Long.parseLong(myId));
        author1.setName(myname);
        author1.setEmail(myemail);
        author1.setPassword(mypassword);
        authorService.update(author1);
        return "redirect:/";
    }

    // @DeleteMapping을 사용할 수도 있지만, DeleteMapping은 form 태그에서 지원하지 않음
    // form 태그에서 DeleteMapping을 지원하지 않는다는 얘기는 action = "delete"를 줄 수 없다는 뜻.
    // 그래서, react나 vue.js와 같은 프론트엔드의 특정한 기술을 통해서 delete 요청을 일반적으로 하므로,
    // restAPI 방식의 개발(json)에서는 DeleteMapping이 가능하다.
    @PostMapping("author/delete")
    public String deleteAuthor(@RequestParam(value="id")String id){
        authorService.delete(Long.parseLong(id));
        return "redirect:/authors";
    }

}

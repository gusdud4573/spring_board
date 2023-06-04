package com.example.spring_board.author.controller;

import com.example.spring_board.author.domain.Author;
import com.example.spring_board.author.etc.AuthorReqeustDto;
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

    @GetMapping("author/login")
    public String authorLogin(){
        return "author/login";
    }

    @GetMapping("authors/new")
    public String authorCreateForm(){
        return "author/author-register";
    }

    @PostMapping("authors/new")
    public String authorCreate(AuthorReqeustDto authorReqeustDto) throws SQLException {


//        방법1.setter방식 : 최초시점 이외의 다른 클래스에서 객체값을 변경함으로서, 유지보수의 어려움발생
//        author1.setName(authorReqeustDto.getName());
//        author1.setEmail(authorReqeustDto.getEmail());
//        author1.setPassword(authorReqeustDto.getPassword());
//        author1.setRole(authorReqeustDto.getRole());
//        author1.setCreateDate(LocalDateTime.now());

////        방법2.생성자 방식(setter배제)
////        문제점은 반드시 매개변수의 순서를 맞춰줘야 한다는 점이고, 매개변수가 많아지게 되면 개발의 어려움.
//        Author author1 = new Author(
//                authorReqeustDto.getName(),
//                authorReqeustDto.getEmail(),
//                authorReqeustDto.getPassword(),
//                authorReqeustDto.getRole()
//        );

//        방법3. builder 패턴 : 매개변수의 순서와 상관없이 객체 생성가능
        Author author1 = Author.builder()
                .password(authorReqeustDto.getPassword())
                .name(authorReqeustDto.getName())
                .email(authorReqeustDto.getEmail())
                .role(authorReqeustDto.getRole())
                .build();

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
    public String authorUpdate(AuthorReqeustDto authorReqeustDto) throws Exception {
        authorService.update(authorReqeustDto);
        return "redirect:/";
    }

    // @DeleteMapping을 사용할 수도 있지만, DeleteMapping은 form 태그에서 지원하지 않음
    // form 태그에서 DeleteMapping을 지원하지 않는다는 얘기는 action = "delete"를 줄 수 없다는 뜻.
    // 그래서, react나 vue.js와 같은 프론트엔드의 특정한 기술을 통해서 delete 요청을 일반적으로 하므로,
    // restAPI 방식의 개발(json)에서는 DeleteMapping이 가능하다.
    @GetMapping("author/delete")
    public String deleteAuthor(@RequestParam(value="id")String id){
        authorService.delete(Long.parseLong(id));
        return "redirect:/authors";
    }

}

package com.example.spring_board.author.service;

import com.example.spring_board.author.domain.Author;
import com.example.spring_board.author.etc.AuthorReqeustDto;
import com.example.spring_board.author.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class AuthorService implements UserDetailsService {
    //authorList, findById, create, 수정, 삭제
    @Autowired
    private AuthorRepository authorRepository;
    //의존성 주입(dependency injection - DI)
    @Autowired
    private PasswordEncoder passwordEncoder;

    //회원목록조회
    public List<Author> findAll(){
        List<Author> authors = authorRepository.findAll();
        return authors;
    }

    public Author findById(Long myId) throws EntityNotFoundException {
        Author authors = authorRepository.findById(myId).orElseThrow(EntityNotFoundException::new);
        return authors;
    }

    //회원가입(등록)
    public void create(Author author) throws SQLException {
        author.setPassword(passwordEncoder.encode(author.getPassword()));
        authorRepository.save(author);
    }

    // 회원정보수정
    public void update(AuthorReqeustDto authorReqeustDto) throws Exception {
        Author author1 = authorRepository.findById(Long.parseLong(authorReqeustDto.getId())).orElseThrow(Exception::new);
        if(author1 == null){
            throw new Exception();
        }else{
            author1.setName(authorReqeustDto.getName());
            author1.setEmail(authorReqeustDto.getEmail());
            author1.setPassword(authorReqeustDto.getPassword());
            authorRepository.save(author1);
        }
    }

    public void delete(Long id){
        // 먼저 DB에서 조회 후에 author객체를 가져온다.
        // 해당 author 객체로 JPA가 delete 할 수 있도록 전달해준다.
        authorRepository.delete(this.findById(id));
    }

    public Author findByEmail(String email) throws EntityNotFoundException {
        Author authors = authorRepository.findByEmail(email);
        return authors;
    }

    // doLogin이라는 spring 내장 메서드가 실행이 될때,
    // UserDetailsService를 구현한 클래스의 LoadByUsername이라는 메서드를 찾는걸로 약속

    @Override
//String username 은 사용자가 화면에 입력한 email 주소를 스프링이 받아서 loadUserByUsername에 넣어준다.
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//      doLogin 내장 기능이 정상 실행되려면, DB에서 조회한 id/pw를 return 해줘야 한다.
        Author author = authorRepository.findByEmail(username);
 /*       if(author == null){

        }*/
        //DB에서 조회한 email, password 권한을 return, 권한이 없다면 emptyList로 return
        return new User(author.getEmail(), author.getPassword(), Collections.emptyList());
    }

}

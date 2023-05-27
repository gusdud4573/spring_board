package com.example.spring_board.author.repository;

import com.example.spring_board.author.domain.Author;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

//DataJpaTest를 사용하면 매 테스트코드가 종료되면 자동으로 DB가 원상복구(롤백)
@DataJpaTest
//test용의 configure를 사용하는 AutoConfigureTestDatabase.Replace가 기본설정
//그런데 기존의 configure(application.yml)를 사용하려면 NONE으로 세팅
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class AuthorRepositoryTest {
    @Autowired
    private AuthorRepository authorRepository;

    //테스트시나리오
    //테스트 데이터를 save, save된 데이터를 다시 조회해서 test 데이터와 비교하면,
    //repository의 save, find 기능이 정상인지 검증할 수 있다.
    //테스트코드의 장점은 부족한 기능을 사전에 알아차릴 수 있다.

    @Test
    public void authorSave(){
        Author authorInput = new Author();
        authorInput.setName("hong");
        authorInput.setEmail("hong@naver.com");
        authorInput.setPassword("1234");

        authorRepository.save(authorInput);
        //저장된 데이터를 다시 조회해서, 입력한 테스트 데이터와 동일한지 검증
        //조회를 해야하는데 id를 모르니까, 기본메서드인 findById를 사용할 수 없음. repository에 추가 필요
        //repository에 findByEmail 추가
        Author authorDB = authorRepository.findByEmail("hong@naver.com");

        //input 값과 조회한 값이 같은지를 비교해보는 방법
/*
        //방법1 : print 해본다.
        System.out.println("name : "+authorDB.getName());
        System.out.println("email : "+authorDB.getEmail());
        System.out.println("password : "+authorDB.getPassword());
*/

        //방법2 : 실행시 에러가 나면 빨간불, 정상이면 파란불로 구분할 수 있도록
        //JUnit의 기능 활용
        //JUnit이란 spring에서 테스트 용도의 tool로 가장 많이 사용되는 라이브러리
        Assertions.assertEquals(authorInput,authorDB);


    }

}

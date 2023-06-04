package com.example.spring_board.aop;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.sql.SQLIntegrityConstraintViolationException;

//AOP(Aspect Oriented Programming) : 관점지향 프로그래밍
//어떤 관점을 기준으로 공통된 로직을 모듈화 하는 방식의 프로그래밍
//어노테이션 중에 Advice, Aspect 등의 키워드가 있으면, 상시적으로 특정한 Event를 catch하는 AOP성 프로그램
@ControllerAdvice
public class ExceptionHandlerAdvice {

// ExceptionHandler의 역할은 EntityNotFoundException이라는 예외 클래스가 발생했을 때 catch 역할
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> notfound(Exception e){
        String context = "<header><h1> 존재하지 않는 화면입니다. </h1></header>";
        return new ResponseEntity<String>(context, HttpStatus.NOT_FOUND);
    }

// SQLIntegrityConstraintViolationException
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<String> duplicateEmail(Exception e){
        String context = "<header><h1> 중복된 이메일입니다. </h1></header>";
        return new ResponseEntity<String>(context, HttpStatus.CONFLICT);
    }


}

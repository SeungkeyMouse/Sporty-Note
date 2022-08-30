package com.sportynote.server.controller;

import lombok.Getter;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

/**
 * 예외 처리용 컨트롤러 입니다.
 * */
//@RestControllerAdvice
@RestController
public class ExceptionController {

//    @ExceptionHandler(NoSuchElementException.class)
//    @ResponseBody
//    public String noItemErrorHandler(NoSuchElementException e){
//        return "Item Not Exists!";
//    }
//
//    @ExceptionHandler(NumberFormatException.class)
//    @ResponseBody
//    public String noItemErrorHandler(NumberFormatException e){
//        return "Invalid Path Variable!";
//    }
    @GetMapping("/12345")
    public String Test() {
        return "Test";
    }
}


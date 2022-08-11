package com.sportynote.server.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

/**
 * 예외 처리용 컨트롤러 입니다.
 * */
@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseBody
    public String noItemErrorHandler(NoSuchElementException e){
        return "Item Not Exists!";
    }

    @ExceptionHandler(NumberFormatException.class)
    @ResponseBody
    public String noItemErrorHandler(NumberFormatException e){
        return "Invalid Path Variable!";
    }

}

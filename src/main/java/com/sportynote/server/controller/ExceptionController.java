package com.sportynote.server.controller;

import com.sportynote.server.domain.UserBasic;
import com.sportynote.server.repository.UserBasicRepository;
import com.sportynote.server.security.UserBasicPrincipal;
import com.sportynote.server.security.user.CurrentUser;
import com.sportynote.server.security.user.UserAccount;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.NoSuchElementException;

/**
 * 예외 처리용 컨트롤러 입니다.
 * */
//@RestControllerAdvice
@RestController
@RequiredArgsConstructor
public class ExceptionController {
        private final UserBasicRepository userBasicRepository;
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
    @GetMapping("/123456")
    public String Test(@ApiIgnore @CurrentUser UserBasicPrincipal userBasicPrincipal) {
        return "123";
    }
}


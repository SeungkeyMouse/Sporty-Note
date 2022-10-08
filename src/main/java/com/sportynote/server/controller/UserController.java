package com.sportynote.server.controller;

import com.sportynote.server.repository.UserBasicRepository;
import com.sportynote.server.security.JwtTokenProvider;
import com.sportynote.server.security.UserBasicPrincipal;
import com.sportynote.server.security.user.CurrentUser;
import com.sportynote.server.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.net.URISyntaxException;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/users")
public class UserController {

    private final UserBasicRepository userBasicRepository;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/")
    public ResponseEntity<?> getUsers() throws URISyntaxException {
        return ResponseEntity.ok(userBasicRepository.findAll());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUsers(@ApiIgnore @CurrentUser UserBasicPrincipal userBasicPrincipal,HttpServletRequest request) throws URISyntaxException {
        String jwtToken = jwtTokenProvider.getTokenFromHeader(request);
        System.out.println(jwtToken);
        return ResponseEntity.ok(userService.deleteUser(userBasicPrincipal.getUserId(),jwtToken));
    }
}

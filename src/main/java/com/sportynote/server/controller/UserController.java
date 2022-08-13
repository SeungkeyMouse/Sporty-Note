package com.sportynote.server.controller;

import com.sportynote.server.repository.UserBasicRepository;
<<<<<<< HEAD
import com.sportynote.server.security.JwtTokenProvider;
import com.sportynote.server.service.UserService;
=======
>>>>>>> e356767f084accefdb147c73c6a441ef3fdb504d
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
<<<<<<< HEAD
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
=======
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

>>>>>>> e356767f084accefdb147c73c6a441ef3fdb504d
import java.net.URISyntaxException;

@RestController
@RequiredArgsConstructor
<<<<<<< HEAD
@RequestMapping(value = "/users")
public class UserController {

    private final UserBasicRepository userBasicRepository;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/")
=======
@RequestMapping(value = "/api/samples")
public class UserController {

    private final UserBasicRepository userBasicRepository;

    @GetMapping("/users")
>>>>>>> e356767f084accefdb147c73c6a441ef3fdb504d
    public ResponseEntity<?> getUsers() throws URISyntaxException {
        return ResponseEntity.ok(userBasicRepository.findAll());
    }

<<<<<<< HEAD
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUsers(HttpServletRequest request) throws URISyntaxException {
        String jwtToken = jwtTokenProvider.getTokenFromHeader(request);
        return ResponseEntity.ok(userService.deleteUsers(jwtToken));
    }

=======
>>>>>>> e356767f084accefdb147c73c6a441ef3fdb504d
}

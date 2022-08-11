package com.sportynote.server.controller;

import com.sportynote.server.repository.UserBasicRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/samples")
public class UserController {

    private final UserBasicRepository userBasicRepository;

    @GetMapping("/users")
    public ResponseEntity<?> getUsers() throws URISyntaxException {
        return ResponseEntity.ok(userBasicRepository.findAll());
    }

}

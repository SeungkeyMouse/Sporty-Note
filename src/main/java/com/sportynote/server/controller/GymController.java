package com.sportynote.server.controller;

import com.sportynote.server.service.GymService;
import com.sportynote.server.type.exception.RestApiException;
import com.sportynote.server.type.exception.UserErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.sportynote.server.type.exception.ExceptionCode.MISMATCH_REFRESH_TOKEN;

@RestController
@RequiredArgsConstructor
@RequestMapping("/gym")
public class GymController {
    private final GymService gymService;
//    @GetMapping("/test")
//    public ResponseEntity<?> getUser() {
//        throw new RestApiException(MISMATCH_REFRESH_TOKEN);
//    }

}

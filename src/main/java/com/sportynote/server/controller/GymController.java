package com.sportynote.server.controller;

import com.sportynote.server.service.GymService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

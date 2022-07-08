package com.sportynote.server.controller;

import com.sportynote.server.repository.RoutineRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;

@RestController
public class RoutineController {

    private final RoutineRepository routineRepository;

    public RoutineController(RoutineRepository routineRepository) {
        this.routineRepository = routineRepository;
    }

    @GetMapping("/routines")
    public ResponseEntity<?> MyRoutines() throws URISyntaxException {
        return ResponseEntity.ok(routineRepository.findAll());
    }
}

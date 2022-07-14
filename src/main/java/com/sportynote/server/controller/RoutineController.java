package com.sportynote.server.controller;

import com.sportynote.server.domain.Routine;
import com.sportynote.server.repository.RoutineRepository;
import com.sportynote.server.service.RoutineService;
import io.swagger.models.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;

@RestController
public class RoutineController {

    private final RoutineRepository routineRepository;

    public RoutineController(RoutineRepository routineRepository) {
        this.routineRepository = routineRepository;
    }

    @GetMapping("/routines")
    public ResponseEntity<?> myRoutines() throws URISyntaxException {
        return ResponseEntity.ok(routineRepository.findAll());
    }

    /** 루틴 Create */
    @PostMapping("/add-routines")
    public ResponseEntity<?> addRoutines(Routine routine) throws URISyntaxException {
        RoutineService.addRoutine("123123",routine);
        return ResponseEntity.ok(HttpStatus.valueOf(201));
        //return ResponseEntity.ok(routineRepository.addRoutine("123123",routine));
    }
    //Routine 1, 기구 N

}

package com.sportynote.server.controller;

import com.sportynote.server.repository.RecordRepository;
import com.sportynote.server.repository.RoutineRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.net.URISyntaxException;

public class RecordController {
    RecordRepository recordRepository;

    public RecordController(RecordRepository recordRepository){
        this.recordRepository=recordRepository;
    }

    //07.12 push 기준 수정중 입니다.
    @GetMapping("/routines")
    public ResponseEntity<?> MyRoutines(String idx, String userid) throws URISyntaxException {
        return ResponseEntity.ok(recordRepository.findByUserId(idx,userid));
    }
}

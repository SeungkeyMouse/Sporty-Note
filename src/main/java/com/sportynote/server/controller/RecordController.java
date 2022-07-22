package com.sportynote.server.controller;

import com.sportynote.server.repository.RecordRepository;
import com.sportynote.server.repository.RoutineRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URISyntaxException;
import java.util.Set;

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
//    /** 루틴 클릭시   Read */
//    @GetMapping("/records")
//    public ResponseEntity<?> myRoutines(@RequestParam("id") String userid) throws URISyntaxException {
//        Set<String> results = routineService.myRoutine(userid);
//        return ResponseEntity.status(HttpStatus.valueOf(status_code)).body("{\"result\":" + results + "}");
//    }

}

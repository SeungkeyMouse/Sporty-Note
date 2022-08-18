package com.sportynote.server.controller;

import com.sportynote.server.domain.Machine;
import com.sportynote.server.domain.Routine;
import com.sportynote.server.repository.RoutineRepository;
import com.sportynote.server.repository.query.RoutineMachineDto;
import com.sportynote.server.repository.query.MachineDto;
import com.sportynote.server.repository.query.RoutineDto;

import com.sportynote.server.service.RoutineService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/routines")
public class RoutineController {

    private final RoutineRepository routineRepository;
    private final RoutineService routineService;

    public RoutineController(RoutineRepository routineRepository, RoutineService routineService) {
        this.routineRepository = routineRepository;
        this.routineService = routineService;
    }
    /**
     * 결과 상태 코드 리턴 변수*/
    private String result;
    private int status_code;

    /** 루틴 Create */
    @PostMapping("/")
    public ResponseEntity<?> addRoutines(@RequestBody RoutineDto routineDto) throws URISyntaxException {
        result = (routineService.addRoutine(routineDto)) ? "success" : "failed";
        status_code = result == "success" ? 201 : 200;
        return ResponseEntity.status(HttpStatus.valueOf(status_code)).body("{\"result\":" + result + "}");
    }

    /** 모든 루틴 Read */
    @GetMapping("/")
    public ResponseEntity<?> myRoutines(@RequestParam("id") String userid) throws URISyntaxException {
        Set<String> results = routineService.myRoutine(userid);
        return ResponseEntity.status(HttpStatus.valueOf(200)).body("{\"result\":" + results + "}");
    }

    /** 루틴 1개 조회 Read */
    @GetMapping("/{routineName}")
    public ResponseEntity<List<RoutineMachineDto>> myRoutineMachines(@PathVariable(value = "routineName") String routineName) throws URISyntaxException {
        List<RoutineMachineDto> results = routineService.findByIdAndRoutineName(routineName);
        return ResponseEntity.status(HttpStatus.valueOf(200)).body(results);
    }

    /** 루틴 수정 Update */
    @PutMapping("/modify-routines")
    public ResponseEntity<?> modifyRoutines(@RequestBody RoutineDto routineDto) throws URISyntaxException {
        result = (routineService.modifyRoutine(routineDto)) ? "success" : "failed";
        status_code = result == "success" ? 201 : 200;
        return ResponseEntity.status(HttpStatus.valueOf(status_code)).body("{\"result\":" + result + "}");
    }

    @DeleteMapping("/{routineName}")
    public ResponseEntity<?> deleteRoutines(@PathVariable(value = "routineName") String routineName) throws URISyntaxException {
        result = (routineService.deleteRoutine(routineName)) ? "success" : "failed";
        status_code = result == "success" ? 200 : 200;
        return ResponseEntity.status(HttpStatus.valueOf(status_code)).body("{\"result\":" + result + "}");
    }


}

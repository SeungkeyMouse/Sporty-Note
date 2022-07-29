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
@RequestMapping("/routine")
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
    @PostMapping("/add-routines")
    public ResponseEntity<?> addRoutines(@RequestBody RoutineDto routineDto) throws URISyntaxException {
        result = (routineService.addRoutine(routineDto)) ? "success" : "failed";
        status_code = result == "success" ? 201 : 200;
        return ResponseEntity.status(HttpStatus.valueOf(status_code)).body("{\"result\":" + result + "}");
    }

    /** 모든 루틴 Read */
    @GetMapping("/routines")
    public ResponseEntity<?> myRoutines(@RequestParam("id") String userid) throws URISyntaxException {
        Set<String> results = routineService.myRoutine(userid);
        return ResponseEntity.status(HttpStatus.valueOf(200)).body("{\"result\":" + results + "}");
    }

    /** 루틴 수정 목록 Read */
    @GetMapping("/my-routine-machine")
    public ResponseEntity<List<RoutineMachineDto>> myRoutineMachines(@RequestParam(value = "routineName") String RoutineName) throws URISyntaxException {
        List<RoutineMachineDto> results = routineService.findByIdAndRoutineName(RoutineName);
        for(RoutineMachineDto dto : results){
            System.out.println(dto.getMachineName());
        }
        return ResponseEntity.status(HttpStatus.valueOf(200)).body(results);
    }

    /** 루틴 수정 Update */
    @PutMapping("/modify-routines")
    public ResponseEntity<?> modifyRoutines(@RequestBody RoutineDto routineDto) throws URISyntaxException {
        result = (routineService.modifyRoutine(routineDto)) ? "success" : "failed";
        status_code = result == "success" ? 201 : 200;
        return ResponseEntity.status(HttpStatus.valueOf(status_code)).body("{\"result\":" + result + "}");
    }

    @DeleteMapping("/delete-routines")
    public ResponseEntity<?> deleteRoutines(@RequestBody RoutineDto routineDto) throws URISyntaxException {
        result = (routineService.deleteRoutine(routineDto)) ? "success" : "failed";
        status_code = result == "success" ? 201 : 200;
        return ResponseEntity.status(HttpStatus.valueOf(status_code)).body("{\"result\":" + result + "}");
    }


}

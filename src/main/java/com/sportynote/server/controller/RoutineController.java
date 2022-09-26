package com.sportynote.server.controller;

import com.sportynote.server.domain.Machine;
import com.sportynote.server.domain.Routine;
import com.sportynote.server.repository.RoutineRepository;
import com.sportynote.server.repository.query.RoutineMachineDto;
import com.sportynote.server.repository.query.MachineDto;
import com.sportynote.server.repository.query.RoutineDto;

import com.sportynote.server.security.UserBasicPrincipal;
import com.sportynote.server.security.user.CurrentUser;
import com.sportynote.server.service.RoutineService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

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

    /** 루틴 Create */
    @PostMapping("")

    public ResponseEntity<?> addRoutines(@ApiIgnore @CurrentUser UserBasicPrincipal userBasicPrincipal,@RequestBody RoutineDto routineDto) throws URISyntaxException {
        return ResponseEntity.status(HttpStatus.valueOf(201)).body(routineService.addRoutine(userBasicPrincipal.getUserId(), routineDto));
    }

    /** 모든 루틴 Read */
    @GetMapping("")
    public ResponseEntity<?> myRoutines(@ApiIgnore @CurrentUser UserBasicPrincipal userBasicPrincipal) throws URISyntaxException {
        return ResponseEntity.status(HttpStatus.valueOf(200)).body(routineService.myRoutine(userBasicPrincipal.getUserId()));
    }

    /** 루틴 1개 조회 Read */
    @GetMapping("/{routineName}")
    public ResponseEntity<List<RoutineMachineDto>> myRoutineMachines(@ApiIgnore @CurrentUser UserBasicPrincipal userBasicPrincipal, @PathVariable(value = "routineName") String routineName) throws URISyntaxException {
        List<RoutineMachineDto> results = routineService.findByIdAndRoutineName(userBasicPrincipal.getUserId(),routineName);
        return ResponseEntity.status(HttpStatus.valueOf(200)).body(results);
    }

    /** 루틴 수정 Update */
    @PutMapping("/modify-routines")
    public ResponseEntity<?> modifyRoutines(@ApiIgnore @CurrentUser UserBasicPrincipal userBasicPrincipal, @RequestBody RoutineDto routineDto) throws URISyntaxException {
        return ResponseEntity.status(HttpStatus.valueOf(201)).body(routineService.modifyRoutine(userBasicPrincipal.getUserId(),routineDto));
    }

    @DeleteMapping("/{routineName}")
    public ResponseEntity<?> deleteRoutines(@ApiIgnore @CurrentUser UserBasicPrincipal userBasicPrincipal, @PathVariable(value = "routineName") String routineName) throws URISyntaxException {
        return ResponseEntity.status(HttpStatus.valueOf(200)).body(routineService.deleteRoutine(userBasicPrincipal.getUserId(),routineName));
    }


}

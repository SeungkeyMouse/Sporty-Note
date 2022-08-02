package com.sportynote.server.controller;

import com.sportynote.server.repository.RecordRepository;
import com.sportynote.server.repository.RoutineRepository;
import com.sportynote.server.repository.query.RecordDto;
import com.sportynote.server.repository.query.RoutineMachineDto;
import com.sportynote.server.service.RecordService;
import com.sportynote.server.service.RoutineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/records")
public class RecordController {
    private final RecordRepository recordRepository;
    private final RoutineService routineService;
    private final RecordService recordService;

    public RecordController(RecordRepository recordRepository, RoutineService routineService,RecordService recordService){
        this.recordRepository=recordRepository;
        this.routineService=routineService;
        this.recordService=recordService;
    }

    /**
     * 결과 상태 코드 리턴 변수*/
    private String result;
    private int status_code;



    /** Read 기록 (그래프) */
    @GetMapping("/{routineName}")
    public ResponseEntity<?> records(@PathVariable(value="routineName") String routineName) throws URISyntaxException {
        List<RoutineMachineDto> results = routineService.findByIdAndRoutineName(routineName);
        return ResponseEntity.status(HttpStatus.valueOf(200)).body(results);
    }

    /** Create 기록 (그래프) 완료마다 */
    @PostMapping("/{machineName}/{sett}")
    public ResponseEntity<?> addRecords(@PathVariable(value="machineName") String machineName, @PathVariable(value="sett") String sett,@RequestBody RecordDto recordDto) throws URISyntaxException {
        result = (recordService.addRecord(recordDto)) ? "success" : "failed";
        status_code = result == "success" ? 201 : 200;
        return ResponseEntity.status(HttpStatus.valueOf(status_code)).body(result);
    }

//    @GetMapping("/")
//    public ResponseEntity<?> myRoutines(@RequestParam("id") String userid) throws URISyntaxException {
//        List<RoutineMachineDto> results = routineService.findByIdAndRoutineName(routineName);
//        for(RoutineMachineDto dto : results){
//            System.out.println(dto.getMachineName());
//        }
//        return ResponseEntity.status(HttpStatus.valueOf(200)).body(results);
//
//        Set<String> results = routineService.myRoutine(userid);
//        return ResponseEntity.status(HttpStatus.valueOf(200)).body("{\"result\":" + results + "}");
//    }

//    /** 루틴 클릭시   Read */
//    @GetMapping("/records")
//    public ResponseEntity<?> myRoutines(@RequestParam("id") String userid) throws URISyntaxException {
//        Set<String> results = routineService.myRoutine(userid);
//        return ResponseEntity.status(HttpStatus.valueOf(status_code)).body("{\"result\":" + results + "}");
//    }

}

package com.sportynote.server.controller;

import com.sportynote.server.domain.Record;
import com.sportynote.server.repository.RecordRepository;
import com.sportynote.server.repository.RoutineRepository;
import com.sportynote.server.repository.query.RecordDto;
import com.sportynote.server.repository.query.RoutineMachineDto;
import com.sportynote.server.security.UserBasicPrincipal;
import com.sportynote.server.security.user.CurrentUser;
import com.sportynote.server.service.RecordService;
import com.sportynote.server.service.RoutineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
     * 결과 상태 코드 리턴 변수 */
    private String result;
    private int status_code;

    /** 나의 기록 보기 날짜별로 */
    @GetMapping("")
    public ResponseEntity<?> calendar(@ApiIgnore @CurrentUser UserBasicPrincipal userBasicPrincipal) throws URISyntaxException {
        List<LocalDate> results = recordService.findByCalendar(userBasicPrincipal.getUserId());
        return ResponseEntity.status(HttpStatus.valueOf(200)).body(results);
    }

    //기록 하나 보기
    @GetMapping("/day/{recordDay}")
    public ResponseEntity<?> recordDay(@ApiIgnore @CurrentUser UserBasicPrincipal userBasicPrincipal, @PathVariable String recordDay) {
        List<RecordDto> results= recordService.findByRecordDay(userBasicPrincipal.getUserId(),recordDay);
        return ResponseEntity.status(HttpStatus.valueOf(200)).body(results);
    }

    /** Read 해당 루틴 불러오기 */
    @GetMapping("/{routineName}")
    public ResponseEntity<?> records(@ApiIgnore @CurrentUser UserBasicPrincipal userBasicPrincipal, @PathVariable(value="routineName") String routineName) throws URISyntaxException {
        List<RoutineMachineDto> results = routineService.findByIdAndRoutineName(userBasicPrincipal.getUserId(), routineName);
        return ResponseEntity.status(HttpStatus.valueOf(200)).body(results);
    }

//    /** Read 기록리스트 (나만의 노트 + 기록) */
//    @GetMapping("/{routine}/")
//    public ResponseEntity<?> records(@PathVariable(value="routineName") String routineName) throws URISyntaxException {
//        List<RoutineMachineDto> results = routineService.findByIdAndRoutineName(routineName);
//        return ResponseEntity.status(HttpStatus.valueOf(200)).body(results);
//    }

    /** Create 기록 (그래프) 완료체크시 */
    @PostMapping("/complete")
    public ResponseEntity<?> addRecords(@ApiIgnore @CurrentUser UserBasicPrincipal userBasicPrincipal, @RequestBody RecordDto recordDto) throws URISyntaxException {
        result = (recordService.addRecord(userBasicPrincipal.getUserId(),recordDto)) ? "success" : "failed";
        status_code = result == "success" ? 201 : 200;
        return ResponseEntity.status(HttpStatus.valueOf(status_code)).body(result);
    }

    /** Delete 기록 (그래프) 완료체크시 */
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteRecords(@ApiIgnore @CurrentUser UserBasicPrincipal userBasicPrincipal, @RequestBody RecordDto recordDto) throws URISyntaxException {
        result = (recordService.deleteRecord(userBasicPrincipal.getUserId(), recordDto)) ? "success" : "failed";
        status_code = result == "success" ? 201 : 200;
        return ResponseEntity.status(HttpStatus.valueOf(status_code)).body(result);
    }

    /** Read 이전 기록, 차트로 보기 */
    @GetMapping("/previous/{machineIdx}")
    public ResponseEntity<?> previousRecord(@ApiIgnore @CurrentUser UserBasicPrincipal userBasicPrincipal, @PathVariable Long machineIdx) throws URISyntaxException {
        List<List<RecordDto>> results = recordService.previousRecord(userBasicPrincipal.getUserId(), machineIdx);
        return ResponseEntity.status(HttpStatus.valueOf(200)).body(results);
    }

}

package com.sportynote.server.controller;

import com.sportynote.server.repository.MachineRepository;
import com.sportynote.server.repository.query.NodeLocationDto;
import com.sportynote.server.service.MachineService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MachineController {

    private final MachineRepository machineRepository;
    private final MachineService machineService;
    //모든 기구리스트 리턴!
    @GetMapping("/machines")
    public ResponseEntity<?> getMachines() throws URISyntaxException {
        return ResponseEntity.ok(machineRepository.findAll());
    }

    //운동기구 하나 클릭시 기구 정보 전송
    @GetMapping("/machines/{machineId}")
    public ResponseEntity<?> getMachineById(@PathVariable Long machineId){
        return ResponseEntity.ok(machineRepository.findById(machineId));
    }

    //즐겨찾기 추가 및 삭제
    @PostMapping("/machines/favorites")
    public ResponseEntity<Long> addFavoriteMachine(@RequestParam("user_id") String userId,
                                                      @RequestParam("machine_idx") Long machineId){
        return ResponseEntity.ok(machineService.addFavorite(userId, machineId));
    }

    //즐겨찾기한 기구들 가져오기
    @GetMapping("/machines/favorites")
    public ResponseEntity<List<?>> getFavoriteMachines(@RequestParam("user_id") String userId){
        return ResponseEntity.ok(machineService.getFavorite(userId));
    }


    //기구별 노드 위치 추가하기
    @PostMapping("/machines/set-node")
    public ResponseEntity<?> setMachineNodeLocation(@RequestBody NodeLocationDto nodeLocationDto){
        return ResponseEntity.ok(machineService.addNodeLocation(nodeLocationDto));
    }

}
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
    @GetMapping("/machine/{machineId}")
    public ResponseEntity<?> getMachineById(@PathVariable Integer machineId){
        return ResponseEntity.ok(machineRepository.findById(machineId));
    }

    //즐겨찾기
    @PostMapping("/machine/favorite")
    public ResponseEntity<Integer> addFavoriteMachine(@RequestParam("user_id") String userId,
                                                      @RequestParam("machine_idx") Integer machineId){
        return ResponseEntity.ok(machineService.addFavorite(userId, machineId));
    }

    //즐겨찾기한 기구들 가져오기
    @GetMapping("/machine/favorite")
    public ResponseEntity<List<?>> getFavoriteMachines(@RequestParam("user_id") String userId){
        return ResponseEntity.ok(machineService.getFavorite(userId));
    }


    //기구별 노드 위치 추가하기
    @PostMapping("/machine/set_node")
    public ResponseEntity<?> setMachineNodeLocation(@RequestBody NodeLocationDto nodeLocationDto){
        return ResponseEntity.ok(machineService.addNodeLocation(nodeLocationDto));
    }

}
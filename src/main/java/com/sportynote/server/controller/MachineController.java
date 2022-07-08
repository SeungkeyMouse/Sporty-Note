package com.sportynote.server.controller;

import com.sportynote.server.domain.Machine;
import com.sportynote.server.repository.MachineRepository;
import com.sportynote.server.service.MachineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

@RestController
@RequiredArgsConstructor
public class MachineController {

    private final MachineRepository machineRepository;
    private final MachineService machineService;
    //모든 기구리스트 리턴
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
    public Integer addFavoriteMachine(@RequestParam("user_id") String userId,
                                                @RequestParam("machine_idx") Integer machineId){

        return machineService.addFavorite(userId, machineId);
    }

}
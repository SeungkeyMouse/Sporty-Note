package com.sportynote.server.controller;

import com.sportynote.server.repository.MachineRepository;
import com.sportynote.server.repository.query.MachineDto;
import com.sportynote.server.repository.query.NodeLocationDto;
import com.sportynote.server.security.UserBasicPrincipal;
import com.sportynote.server.security.user.CurrentUser;
import com.sportynote.server.service.MachineService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/machines")
@Slf4j
public class MachineController {

    private final MachineRepository machineRepository;
    private final MachineService machineService;

    /**
     * 기구 검색 관련기능
     * @return
     * @throws URISyntaxException
     */
    //모든 기구리스트 리턴!
    @GetMapping("/")
    public ResponseEntity<?> getMachines() throws URISyntaxException {
        return ResponseEntity.ok(machineService.getMachines());
    }

    //운동기구 하나 클릭시 기구 정보 전송
    @GetMapping("/{machineId}")
    public ResponseEntity<?> getMachineById(@PathVariable Long machineId){
        log.info("getMachineById; machineId={}", machineId);
        MachineDto machine = machineService.getMachineById(machineId);
        if (machine == null)
            machine = new MachineDto();

        log.info("machine : "+ machine.toString());
        return ResponseEntity.ok(machine);
    }

    //
    //즐겨찾기 추가
    @PostMapping("/favorites")
    public ResponseEntity<Long> addFavoriteMachine(@RequestParam("user_id") String userId,
                                                      @RequestParam("machine_idx") Long machineId){
        return ResponseEntity.ok(machineService.addFavorite(userId, machineId));
    }

    /**
     * 즐겨찾기 삭제
     * @param userFavoriteIdx
     * @return
     */
    @DeleteMapping("/favorites")
    public ResponseEntity<Long> addFavoriteMachine(@RequestParam("user_favorite_idx") Long userFavoriteIdx){
        return ResponseEntity.ok(machineService.deleteFavorite(userFavoriteIdx));
    }

    //즐겨찾기한 기구들 가져오기
    @GetMapping("/favorites")
    public ResponseEntity<List<?>> getFavoriteMachines(@RequestParam("user_id") String userId){
        return ResponseEntity.ok(machineService.getFavorite(userId));
    }


    /**
     * 기구별 노드 세팅
     * @param nodeLocationDto
     * @return
     */
    //기구별 노드 위치 추가하기
    @PostMapping("/machines/set-node")
    public ResponseEntity<?> setMachineNodeLocation(@RequestBody NodeLocationDto nodeLocationDto){
        return ResponseEntity.ok(machineService.addNodeLocation(nodeLocationDto));
    }

}
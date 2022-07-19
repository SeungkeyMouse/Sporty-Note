package com.sportynote.server.controller;

import com.sportynote.server.repository.NoteNodeRepository;
import com.sportynote.server.repository.NoteRepository;
import com.sportynote.server.repository.query.MachineDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class NoteController {
    private final NoteRepository noteRepository;
    private final NoteNodeRepository noteNodeRepository;

    //GET
    //2. 해당하는 운동의 다른사람 '노드' 모두 불러오기(전체탭) ex) 벤치프레스의 [전체] 노트
    //3. 해당하는 운동의 나의 '노드' 모두 불러오기(MY탭) ex) 벤치프레스의 [MY] 노트
    //4. 해당하는 운동의 추천 '노드' 모두 불러오기(추천탭) ex) 벤치프레스의 [추천] 노트

    //POST
    //1. 나의 <노트>에 '노드' 저장(UPDATE ALL? or 하나씩 UPDATE?)
    //2.

    //1. 나의 <노트한 [기구]> 모두 불러오기(운동 종류 상관없이) ex) 내가 생성한 노트가 있는 기구(제목, id등)
    @GetMapping("/note")
    public ResponseEntity<List<MachineDto>> getAllMyNotedMachines(@RequestParam("user_id") String userId){
        return ResponseEntity.ok(noteRepository.findNotedMachineByUserId(userId));
//        return ResponseEntity.ok(noteNodeRepository.findAll());
    }
}

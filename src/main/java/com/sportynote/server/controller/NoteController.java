package com.sportynote.server.controller;

import com.sportynote.server.Enum.NodeType;
import com.sportynote.server.domain.NoteNode;
import com.sportynote.server.repository.NoteNodeRepository;
import com.sportynote.server.repository.NoteRepository;
import com.sportynote.server.repository.query.*;
import com.sportynote.server.service.NoteService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class NoteController {
    private final NoteRepository noteRepository;
    private final NoteNodeRepository noteNodeRepository;
    private final NoteService noteService;

    //GET
    //3. 해당하는 운동의 다른사람 '노드' 모두 불러오기(전체탭) ex) 벤치프레스의 [전체] 노트
    //4. 해당하는 운동의 추천 '노드' 모두 불러오기(추천탭) ex) 벤치프레스의 [추천] 노트



    //1. 나의 <노트한 [기구]> 모두 불러오기(운동 종류 상관없이) ex) 내가 생성한 노트가 있는 기구(제목, id등)
    @GetMapping("/notes/machines")
    public ResponseEntity<List<MachineDto>> getAllMyNotedMachines(@RequestParam("userId") String userId){
        return ResponseEntity.ok(noteService.getAllMyNotedMachines(userId));
    }


    //2. 해당하는 운동의 나의 '노드' 모두 불러오기(MY탭) ex) 벤치프레스의 [MY] 노트
    @GetMapping("/notes")
    public ResponseEntity<NoteDto> getMyNote(@RequestParam("userId") String userId,
                                                @RequestParam("machineId") Long machineId){
        return ResponseEntity.ok(noteService.findMyNoteNodes(userId, machineId));
    }
}

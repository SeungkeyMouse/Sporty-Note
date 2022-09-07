package com.sportynote.server.controller;

import com.sportynote.server.Enum.NodeType;
import com.sportynote.server.domain.NoteNode;
import com.sportynote.server.repository.NoteNodeRepository;
import com.sportynote.server.repository.NoteRepository;
import com.sportynote.server.repository.query.*;
import com.sportynote.server.security.UserBasicPrincipal;
import com.sportynote.server.security.user.CurrentUser;
import com.sportynote.server.service.NoteService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notes")
public class NoteController {
    private final NoteService noteService;

    //GET
    //4. 해당하는 운동의 다른사람 '노드' 모두 불러오기(전체탭) ex) 벤치프레스의 [전체] 노트
    //5. 해당하는 운동의 추천 '노드' 모두 불러오기(추천탭) ex) 벤치프레스의 [추천] 노트


    //1. 나의 <노트한 [기구]> 모두 불러오기(운동 종류 상관없이) ex) 내가 생성한 노트가 있는 기구(제목, id등)
    @GetMapping("/machines")
    public ResponseEntity<List<MachineDto>> getAllMyNotedMachines(@ApiIgnore @CurrentUser UserBasicPrincipal userBasicPrincipal){
        System.out.println(userBasicPrincipal.getUserId());
        return ResponseEntity.ok(noteService.getAllMyNotedMachines(userBasicPrincipal.getUserId()));
    }


    //2. 해당하는 운동의 나의 '노드' 모두 불러오기(MY탭) ex) 벤치프레스의 [MY] 노트
    @GetMapping("/")
    public ResponseEntity<NoteDto> getMyNote(@ApiIgnore @CurrentUser UserBasicPrincipal userBasicPrincipal,
                                                @RequestParam("machineId") Long machineId){
        return ResponseEntity.ok(noteService.findMyNoteNodes(userBasicPrincipal.getUserId(), machineId));
    }

    //3. [일반] 탭 노트 내용 불러오기
    @GetMapping("/general")
    public ResponseEntity<NoteDto> getNormalNote(@RequestParam("machineId") Long machineId){
        return ResponseEntity.ok(noteService.findGeneralNoteNodes(machineId));
    }
}

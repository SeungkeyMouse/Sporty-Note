package com.sportynote.server.controller;

import com.sportynote.server.repository.query.*;
import com.sportynote.server.security.UserBasicPrincipal;
import com.sportynote.server.security.user.CurrentUser;
import com.sportynote.server.service.NoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notes")
@Slf4j
public class NoteController {
    private final NoteService noteService;

    //GET
    //4. 해당하는 운동의 다른사람 '노드' 모두 불러오기(전체탭) ex) 벤치프레스의 [전체] 노트
    //5. 해당하는 운동의 추천 '노드' 모두 불러오기(추천탭) ex) 벤치프레스의 [추천] 노트


    //1. 나의 <노트한 [기구]> 모두 불러오기(운동 종류 상관없이) ex) 내가 생성한 노트가 있는 기구(제목, id등)
    @GetMapping("/machines")
    public ResponseEntity<List<MachineDto>> getAllMyNotedMachines(@ApiIgnore @CurrentUser UserBasicPrincipal userBasicPrincipal){
        return ResponseEntity.ok(noteService.getAllMyNotedMachines(userBasicPrincipal.getUserId()));
    }


    //2. 해당하는 운동의 나의 '노드' 모두 불러오기(MY탭) ex) 벤치프레스의 [MY] 노트
    @GetMapping()
    public ResponseEntity<NoteDto> getMyNote(@ApiIgnore @CurrentUser UserBasicPrincipal userBasicPrincipal,
                                                @RequestParam("machineId") Long machineId){
        log.info("getMyNote; machineId={}", machineId);
        NoteDto myNoteNodes = noteService.findMyNoteNodes(userBasicPrincipal.getUserId(), machineId);
        log.info("myNotes : "+ myNoteNodes.toString());
        return ResponseEntity.ok(myNoteNodes);
    }

    //3. [일반] 탭 노트 내용 불러오기
    @GetMapping("/general")
    public ResponseEntity<NoteDto> getNormalNote(@RequestParam("machineId") Long machineId){
        log.info("getNormalNote; machineId={}", machineId);
        NoteDto generalNoteNodes = noteService.findGeneralNoteNodes(machineId);
        log.info("generalNote : "+ generalNoteNodes.toString());

        return ResponseEntity.ok(generalNoteNodes);
    }

    /*@PostMapping("/general/list")
    public ResponseEntity<List<NoteDto>> getGeneralNotes(@RequestBody GeneralNotesDto generalNotesDto){
        log.info("getGeneralNotes; generalNoteDto.getMachineIds={}", generalNotesDto.getMachineIdxs().toArray());
        List<NoteDto> generalNotes = noteService.findGeneralNotes(generalNotesDto.getMachineIdxs());
        log.info("generalNotes : " + generalNotes.toString());

        return  ResponseEntity.ok(generalNotes);
    }*/

    @GetMapping("/general/list")
    public ResponseEntity<List<NoteDto>> getGeneralNotes(@RequestParam List<String> list){
        log.info("getGeneralNotes; generalNoteDto.getMachineIds={}", list.toArray());
        List<NoteDto> generalNotes = noteService.findGeneralNotes(list);
        log.info("generalNotes : " + generalNotes.toString());

        return  ResponseEntity.ok(generalNotes);
    }
}

package com.sportynote.server.controller;

import com.sportynote.server.repository.query.NodeCreateDto;
import com.sportynote.server.repository.query.NodeSetCreateDto;
import com.sportynote.server.repository.query.NodeUpdateDto;
import com.sportynote.server.repository.query.NoteDto;
import com.sportynote.server.security.UserBasicPrincipal;
import com.sportynote.server.security.user.CurrentUser;
import com.sportynote.server.service.NoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequiredArgsConstructor
@RequestMapping("/nodes")
@Slf4j
public class NoteNodeController {
    private final NoteService noteService;

    //POST
    //1. 나의 <노트>에 '노드' 저장(하나씩 UPDATE)
    @PostMapping
    public ResponseEntity<Long> addNoteNode(@ApiIgnore @CurrentUser UserBasicPrincipal userBasicPrincipal, @RequestBody NodeCreateDto nodeCreateDto){
        log.info("addNoteNode; nodeCreateDto={}", nodeCreateDto.toString());
        Long response = noteService.addNoteNode(userBasicPrincipal.getUserId(), nodeCreateDto);
        log.info("response : "+ response.toString());

        return ResponseEntity.ok(response);
    }

    //2. 노드 수정
    @PutMapping
    public ResponseEntity<Long> updateNoteNode(@ApiIgnore @CurrentUser UserBasicPrincipal userBasicPrincipal, @RequestBody NodeUpdateDto nodeDto){
        log.info("updateNoteNode; nodeDto={}", nodeDto.toString());
        Long response = noteService.updateNoteNode(nodeDto);
        log.info("response : "+ response.toString());

        return ResponseEntity.ok(response);
    }

    //3. 노드 삭제
    @DeleteMapping
    public ResponseEntity<Boolean> deleteNoteNode(@ApiIgnore @CurrentUser UserBasicPrincipal userBasicPrincipal,@RequestParam("note_node_idx") Long noteNodeIdx){
        log.info("deleteNoteNode; nodeDto={}", noteNodeIdx.toString());
        Long deleteNoteIdx = noteService.deleteNoteNode(noteNodeIdx);
        boolean response = noteService.deleteNoteIfNodeZero(deleteNoteIdx);
        log.info("response : "+ response);

        return ResponseEntity.ok(response);
    }


    /**
     * NoteNodeSet [일반]탭에 들어가는 내용을 세팅하는 api
     */
    private String result;
    private int status_code;
    //POST
    //1. <노트>에 '노드' 저장(하나씩 UPDATE)
    @PostMapping("/set")
    public ResponseEntity<?> addNoteNodeSet(@RequestBody NodeSetCreateDto nodeCreateDto){
        result = (noteService.addNoteNodeSet(nodeCreateDto)) ? "success" : "failed";
        status_code = result == "success" ? 201 : 200;
        return ResponseEntity.status(HttpStatus.valueOf(status_code)).body(result);
    }

    //2. 노드 수정
    @PutMapping("/set")
    public ResponseEntity<?> updateNoteNodeSet(@RequestBody NodeUpdateDto nodeDto){
        result = (noteService.updateNoteNodeSet(nodeDto)) ? "success" : "failed";
        status_code = result == "success" ? 201 : 200;
        return ResponseEntity.status(HttpStatus.valueOf(status_code)).body(result);
    }

    //3. 노드 삭제
    @DeleteMapping("/set")
    public ResponseEntity<?> deleteNoteNodeSet(@RequestParam("note_node_idx") Long nodeIdx){
        result = (noteService.deleteNoteNodeSet(nodeIdx)) ? "success" : "failed";
        status_code = result == "success" ? 201 : 200;
        return ResponseEntity.status(HttpStatus.valueOf(status_code)).body(result);
    }
}

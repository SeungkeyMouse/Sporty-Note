package com.sportynote.server.controller;

import com.sportynote.server.repository.query.NodeCreateDto;
import com.sportynote.server.repository.query.NodeUpdateDto;
import com.sportynote.server.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class NoteNodeController {
    private final NoteService noteService;

    //POST
    //1. 나의 <노트>에 '노드' 저장(하나씩 UPDATE)
    @PostMapping("/nodes")
    public ResponseEntity<Long> addNoteNode(@RequestBody NodeCreateDto nodeCreateDto){
        return ResponseEntity.ok(noteService.addNoteNode(nodeCreateDto));
    }

    //2. 노드 수정
    @PutMapping("/nodes")
    public ResponseEntity<Long> updateNoteNode(@RequestBody NodeUpdateDto nodeDto){
        return ResponseEntity.ok(noteService.updateNoteNode(nodeDto));
    }

    //3. 노드 삭제
    @DeleteMapping("/nodes")
    public ResponseEntity<Long> deleteNoteNode(@RequestParam("note_node_idx") Long noteNodeIdx){
        return ResponseEntity.ok(noteService.deleteNoteNode(noteNodeIdx));
    }
}

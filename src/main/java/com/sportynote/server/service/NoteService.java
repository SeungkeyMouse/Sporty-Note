package com.sportynote.server.service;

import com.sportynote.server.Enum.NodeType;
import com.sportynote.server.domain.Machine;
import com.sportynote.server.domain.Note;
import com.sportynote.server.domain.NoteNode;
import com.sportynote.server.repository.MachineRepository;
import com.sportynote.server.repository.NoteNodeRepository;
import com.sportynote.server.repository.NoteRepository;
import com.sportynote.server.repository.UserBasicRepository;
import com.sportynote.server.repository.query.NodeCreateDto;
import com.sportynote.server.repository.query.NodeDto;
import com.sportynote.server.repository.query.NodeUpdateDto;
import com.sportynote.server.repository.query.NoteDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Node;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class NoteService {
    private final NoteNodeRepository nodeRepository;
    private final NoteRepository noteRepository;
    private final UserBasicRepository userBasicRepository;
    private final MachineRepository machineRepository;

    public Integer addNoteNode(NodeCreateDto nodeCreateDto) {

        Optional<Note> optNote = noteRepository.findByIds(nodeCreateDto.getUserId(), nodeCreateDto.getMachineId());
        Note note;
        //해당 기구에 대한 노트가 생성되지 않은 경우 -> 노트 먼저 생성
        if(!optNote.isPresent()){
            note = Note.createNote(
                    userBasicRepository.findById(nodeCreateDto.getUserId()),
                    machineRepository.findById(nodeCreateDto.getMachineId())
            );
            noteRepository.save(note);
        }else{//해당 기구에 대한 노트가 이미 있는 경우 가져옴.
            note = optNote.get();
        }
        //이후 노드 생성
        NoteNode node = NoteNode.createNode(note,nodeCreateDto);
        nodeRepository.save(node);
        //노트-노드연결
        note.getNoteNode().add(node);

        return note.getIdx();
    }

    public NoteDto findMyNoteNodes(String userId, Integer machineId) {
        return noteRepository.findMyNoteNodes(userId, machineId);
    }

    public Integer updateNoteNode(NodeUpdateDto nodeDto) {
        NoteNode node = nodeRepository.findById(nodeDto.getIdx());
        //변경감지 - 수정
        node.setType(NodeType.findNodeType(nodeDto.getType().getEngName()));
        node.setColor(nodeDto.getColor());
        node.setText(nodeDto.getText());
        node.setPictureUrl(nodeDto.getPictureUrl());

        return node.getIdx();
    }

}

package com.sportynote.server.service;

import com.sportynote.server.Enum.NodeType;
import com.sportynote.server.domain.*;
import com.sportynote.server.repository.*;
import com.sportynote.server.repository.query.*;
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

    private final NodeLocationSetRepository nodeLocationSetRepository;

    private final UserFavoriteRepository userFavoriteRepository;

    public Long addNoteNode(NodeCreateDto nodeCreateDto) {

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
        NoteNode node;

        //노드의 위치를 x,y=0F로 설정한 경우에는 세팅되어있는 부위의 위치를 집어넣게 함.
        if(nodeCreateDto.getX_location().compareTo(0F)==0 && nodeCreateDto.getY_location().compareTo(0F)==0) {
            Optional<NodeLocationSet> optNLS= nodeLocationSetRepository.findByMachineIdAndNodeType(
                    nodeCreateDto.getMachineId(),
                    NodeType.findNodeType(nodeCreateDto.getType().getEngName())
            );
            //세팅되어있는 부위의 위치가 있다면 해당 값을 집어넣음
            if(!optNLS.isEmpty()){
                NodeLocationSet nls = optNLS.get();
                nodeCreateDto.setX_location(nls.getX_location());
                nodeCreateDto.setY_location(nls.getY_location());
            }
        }
        //노드의 값을 임의로 0F가 아닌 요청을 했을 경우에는 해당 요청에 맞는 x,y값을 넣음.
        node = NoteNode.createNode(note, nodeCreateDto);
        nodeRepository.save(node);

        //노트-노드연결
        note.getNoteNode().add(node);

        return note.getIdx();
    }

    public NoteDto findMyNoteNodes(String userId, Long machineId) {
        return noteRepository.findMyNoteNodes(userId, machineId);
    }

    public Long updateNoteNode(NodeUpdateDto nodeDto) {
        NoteNode node = nodeRepository.findById(nodeDto.getNodeIdx());
        //변경감지 - 수정
        node.setType(NodeType.findNodeType(nodeDto.getType().getEngName()));
        node.setColor(nodeDto.getColor());
        node.setText(nodeDto.getText());
        node.setPictureUrl(nodeDto.getPictureUrl());

        return node.getIdx();
    }

    public List<MachineDto> getAllMyNotedMachines(String userId) {
        List<MachineDto> notedMachines = noteRepository.findNotedMachineByUserId(userId);
        for (MachineDto notedMachine : notedMachines) {
            Optional<UserFavorite> optUf = userFavoriteRepository.findByUserIdAndMachineId(userId, notedMachine.getMachineIdx());
            if(optUf.isEmpty()) continue;
            if(optUf.get().isDeleted()) continue;
            UserFavorite uf = optUf.get();
            notedMachine.setUserFavoriteIdx(uf.getIdx());
        }
        return notedMachines;
    }
}

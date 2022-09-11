package com.sportynote.server.service;

import com.sportynote.server.Enum.NodeType;
import com.sportynote.server.domain.*;
import com.sportynote.server.repository.*;
import com.sportynote.server.repository.query.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final NoteNodeSetRepository nodeSetRepository;

    public Long addNoteNode(String userId, NodeCreateDto nodeCreateDto) {

        Optional<Note> optNote = noteRepository.findById(nodeCreateDto.getNoteIdx());
        Note note;
        //해당 기구에 대한 노트가 생성되지 않은 경우 -> 노트 먼저 생성
        if (!optNote.isPresent()) {
            note = Note.createNote(
                    userBasicRepository.findById(userId),
                    machineRepository.findById(nodeCreateDto.getMachineIdx())
            );
            noteRepository.save(note);
        } else {//해당 기구에 대한 노트가 이미 있는 경우 가져옴.
            note = optNote.get();
        }
        //이후 노드 생성
        NoteNode node;

        //노드의 위치를 x,y=0F로 설정한 경우에는 세팅되어있는 부위의 위치를 집어넣게 함.
        if (nodeCreateDto.getX_location().compareTo(0F) == 0 && nodeCreateDto.getY_location().compareTo(0F) == 0) {
            Optional<NodeLocationSet> optNLS = nodeLocationSetRepository.findByMachineIdAndNodeType(
                    nodeCreateDto.getMachineIdx(),
                    NodeType.findNodeType(nodeCreateDto.getType().getEngName())
            );
            //세팅되어있는 부위의 위치가 있다면 해당 값을 집어넣음
            if (!optNLS.isEmpty()) {
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
            if (optUf.isEmpty()) continue;
            if (optUf.get().isDeleted()) continue;
            UserFavorite uf = optUf.get();
            notedMachine.setUserFavoriteIdx(uf.getIdx());
        }
        return notedMachines;
    }

    public Long deleteNoteNode(Long nodeIdx) {
        userBasicPrincipal.getUserId();
        NoteNode node = nodeRepository.findById(nodeIdx);
        return nodeRepository.delete(node);
    }

    /**
     * 세팅용 노드 CRUD
     *
     * @param machineId 노트 생성 안하고 nodeLocation처럼 바로 Machine에 꽂힌 노드
     * @return
     */
    public NoteDto findGeneralNoteNodes(Long machineId) {
        NoteDto generalNote = nodeSetRepository.findGeneralNoteNodes(machineId);
        Machine m = machineRepository.findById(machineId);
        MachineDto machineDto = new MachineDto(m.getIdx(), m.getKrMachineName(), m.getEngMachineName(),
                m.getTargetArea(), m.getImageUrl1(), m.getImageUrl2(), m.getVideoUrl1());
        generalNote.setMachineDto(machineDto);

        generalNote.setNoteIdx(0L);
        return generalNote;
    }

    public boolean addNoteNodeSet(NodeSetCreateDto nodeSetCreateDto) {
        Machine m = machineRepository.findById(nodeSetCreateDto.getMachineIdx());

        NoteNodeSet noteNodeSet = NoteNodeSet.createNode(m, nodeSetCreateDto);

        nodeSetRepository.save(noteNodeSet);

        return true;
    }

    public boolean updateNoteNodeSet(NodeUpdateDto nodeDto) {
        NoteNodeSet node = nodeSetRepository.findById(nodeDto.getNodeIdx());
        //변경감지 - 수정
        node.setType(NodeType.findNodeType(nodeDto.getType().getEngName()));
        node.setColor(nodeDto.getColor());
        node.setText(nodeDto.getText());
        node.setPictureUrl(nodeDto.getPictureUrl());

        return true;
    }
    public boolean deleteNoteNodeSet(Long nodeIdx) {
        NoteNodeSet node = nodeSetRepository.findById(nodeIdx);
        nodeSetRepository.delete(node);
        return true;
    }

}

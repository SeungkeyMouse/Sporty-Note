package com.sportynote.server.service;

import com.sportynote.server.type.NodeType;
import com.sportynote.server.domain.*;
import com.sportynote.server.repository.*;
import com.sportynote.server.repository.query.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class NoteService {
    private final NoteNodeRepository nodeRepository;
    private final NoteRepository noteRepository;
    private final UserBasicRepository userBasicRepository;
    private final MachineRepository machineRepository;

    private final NodeLocationSetRepository nodeLocationSetRepository;

    private final UserFavoriteRepository userFavoriteRepository;
    private final NoteNodeSetRepository nodeSetRepository;

    public Long addNoteNode(String userId, NodeCreateDto nodeCreateDto) {

        List<Note> noteList = noteRepository.findByMachineId(nodeCreateDto.getMachineIdx());
        Note note;
        //해당 기구에 대한 노트가 생성되지 않은 경우 -> 노트 먼저 생성
        if (noteList.size()==0) {
            note = Note.createNote(
                    userBasicRepository.findById(userId),
                    machineRepository.findById(nodeCreateDto.getMachineIdx())
            );
            noteRepository.save(note);
        } else {//해당 기구에 대한 노트가 이미 있는 경우 가져옴.
            note = noteList.get(0);
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
        NoteDto myNoteNodes = noteRepository.findMyNoteNodes(userId, machineId);
        if(myNoteNodes.getNodeDtos().size()==0){//0인경우 머신 정보만 넘겨주고 나머지 map은 비어있음.
            myNoteNodes.setNoteIdx(-1L);
            Machine machine = machineRepository.findById(machineId);
            MachineDto machineDto = new MachineDto(machine.getIdx(), machine.getKrMachineName(),machine.getEngMachineName(),
                    machine.getTargetArea(), machine.getImageUrl1(), machine.getImageUrl2(), machine.getVideoUrl1());
            myNoteNodes.setMachineDto(machineDto);
        }

        return myNoteNodes;
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

    /**
     * 노드를 삭제하되, 만약 그것이 노트의 마지막 노드일 경우, "노트"도 삭제.
     * @param nodeIdx
     * @return 지워진 노드 idx
     */
    public Long deleteNoteNode(Long nodeIdx) {
        NoteNode node = nodeRepository.findById(nodeIdx);
        Long deletedNoteIdx = nodeRepository.delete(node);

        return deletedNoteIdx;
    }
    public boolean deleteNoteIfNodeZero(Long deletedNoteIdx){
        List<NoteNode> existsNote = nodeRepository.findByNoteId(deletedNoteIdx);
        System.out.println(existsNote.toString());
        if(existsNote.size()==0) {
            Optional<Note> optNote = noteRepository.findById(deletedNoteIdx);
            optNote.ifPresent(noteRepository::delete);
            return true;
        }
        return false;
    }

    /**
     * 세팅용 노드 CRUD
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
    public List<NoteDto> findGeneralNotes(List<String> machineIds){
        List<NoteDto> generalNoteList = new ArrayList<>();
        for (String machineIdx : machineIds) {
            Long machineId = Long.valueOf(machineIdx);

            NoteDto generalNoteNodes = findGeneralNoteNodes(machineId);
            if(generalNoteNodes.getNodeDtos().size()!=0)
                generalNoteList.add(generalNoteNodes);
        }
        return generalNoteList;
    }

    public boolean addNoteNodeSet(NodeSetCreateDto nodeSetCreateDto) {
        List<Machine> m = machineRepository.findByName(nodeSetCreateDto.getMachineName());
        if(m.size() != 1){
            return false;
        }

        NoteNodeSet noteNodeSet = NoteNodeSet.createNode(m.get(0), nodeSetCreateDto);

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

package com.sportynote.server.repository;

import com.sportynote.server.Enum.NodeType;
import com.sportynote.server.domain.Machine;
import com.sportynote.server.domain.Note;
import com.sportynote.server.domain.NoteNode;
import com.sportynote.server.repository.query.MachineDto;
import com.sportynote.server.repository.query.NodeDto;
import com.sportynote.server.repository.query.NoteDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class NoteRepository {
    private final EntityManager em;

    public void save(Note note) {
        em.persist(note);
    }

    /**
     * 내가 만든 노트가 있는 기구들만 호출
     */
    public List<MachineDto> findNotedMachineByUserId(String userId) {
        List<Note> noteList = em.createQuery("select n from Note n"
                                + " join fetch n.machine"
                                + " join fetch n.userBasic where n.userBasic.userId=: userId"
                        , Note.class)
                .setParameter("userId", userId)
                .getResultList();

//      다 필요한데 해당 유저를 찾아야하면 아래와 같이 필터링
       /*
       noteList.stream().filter(note::isUserId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException());
        */

        List<MachineDto> machineDtoList = new ArrayList<>();
        for (Note note : noteList) {
            Machine machine = note.getMachine();
            MachineDto machineDto = new MachineDto(machine.getIdx(), machine.getKrMachineName(),machine.getEngMachineName(),
                    machine.getTargetArea(), machine.getUrl());
            machineDtoList.add(machineDto);
        }

        return machineDtoList;
    }

    public Optional<Note> findByIds(String userId, Long machineId) {
        Optional<Note> note = null;
        try {
            note = Optional.ofNullable(em.createQuery("select n from Note n " +
                            "where n.userBasic.userId=:userId " +
                            "and n.machine.idx =: machineId ", Note.class)
                    .setParameter("userId", userId)
                    .setParameter("machineId", machineId)
                    .getSingleResult());
        } catch (NoResultException e) {
            note = Optional.empty();
        } finally {
            return note;
        }
    }

    public NoteDto findMyNoteNodes(String userId, Long machineId) {
        List<NoteNode> resultList = em.createQuery("select nd from NoteNode nd"
                                + " join fetch nd.note n"
                                + " join fetch n.machine"
                                + " where n.userBasic.userId=: userId and n.machine.idx=: machineId "
                        , NoteNode.class)
                .setParameter("userId", userId)
                .setParameter("machineId", machineId)
                .getResultList();

        //NoteDto에 담을 정보 세팅
        NoteDto noteDto = new NoteDto();

        //1. 머신에 대한 기본 정보
        Machine machine = resultList.get(0).getNote().getMachine();
        MachineDto machineDto = new MachineDto(machine.getIdx(), machine.getKrMachineName(),machine.getEngMachineName(),
                machine.getTargetArea(), machine.getUrl());

        //2. "노드"들에 대한 기본 정보
        Map<NodeType, List<NodeDto>> nodeMap = new HashMap<>();//key: 노트타입, value: 노드Dto리스트
        for (NoteNode node : resultList) {
            NodeType key = node.getType();

            //노드-> 노드DTO
            NodeDto nodeDto = new NodeDto(node.getColor(),
                    machineId,node.getIdx(), node.getType(), node.getColor(), node.getText(), node.getX_location(), node.getY_location(),
                    node.getPictureUrl());

            //각 node의 타입별로 Map에 넣어줌
            if (!nodeMap.containsKey(key)) {//Map에 아직 노드가 할당되지 않은 경우 리스트 선언
                nodeMap.put(key, new ArrayList<>());
            }
            nodeMap.get(key).add(nodeDto);
        }

        noteDto.setMachineDto(machineDto);
        noteDto.setNodeDtos(nodeMap);

        return noteDto;
    }
}

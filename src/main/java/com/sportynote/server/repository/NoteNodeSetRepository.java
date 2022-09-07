package com.sportynote.server.repository;

import com.sportynote.server.Enum.NodeType;
import com.sportynote.server.domain.Machine;
import com.sportynote.server.domain.Note;
import com.sportynote.server.domain.NoteNode;
import com.sportynote.server.domain.NoteNodeSet;
import com.sportynote.server.repository.query.MachineDto;
import com.sportynote.server.repository.query.NodeDto;
import com.sportynote.server.repository.query.NoteDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.*;

@Repository
@RequiredArgsConstructor
public class NoteNodeSetRepository {
    private final EntityManager em;

    public void save(NoteNodeSet noteNodeSet) {
        em.persist(noteNodeSet);
    }
    public NoteNodeSet findById(Long nodeId){
        return em.createQuery("select n from NoteNodeSet n where n.idx =: nodeId", NoteNodeSet.class)
                .setParameter("nodeId", nodeId)
                .getSingleResult();

    }
    public Long delete(NoteNodeSet node) {
        em.remove(node);
        return node.getIdx();
    }
    public NoteDto findGeneralNoteNodes(Long machineIdx) {
        List<NoteNodeSet> resultList = em.createQuery("select nd from NoteNodeSet nd"
                                + " where nd.machine.idx=: machineId" +
                                " and nd.deleted=false "
                        , NoteNodeSet.class)
                .setParameter("machineId", machineIdx)
                .getResultList();

        //NoteDto에 담을 정보 세팅
        NoteDto noteDto = new NoteDto();
        noteDto.setNoteIdx(0L);

        //1. "노드"들에 대한 기본 정보
        Map<NodeType, List<NodeDto>> nodeMap = new HashMap<>();//key: 노트타입, value: 노드Dto리스트
        for (NoteNodeSet node : resultList) {
            NodeType key = node.getType();
            //노드-> 노드DTO
            NodeDto nodeDto = new NodeDto(
                    "[일반]",
                    machineIdx,
                    node.getIdx(),
                    node.getType(),
                    node.getColor(),
                    node.getText(),
                    node.getX_location(),
                    node.getY_location(),
                    node.getUpdatedAt(),
                    node.getPictureUrl()
            );

            //각 node의 타입별로 Map에 넣어줌
            if (!nodeMap.containsKey(key)) {//Map에 아직 노드가 할당되지 않은 경우 리스트 선언
                nodeMap.put(key, new ArrayList<>());
            }
            nodeMap.get(key).add(nodeDto);
        }
        noteDto.setNodeDtos(nodeMap);

        return noteDto;
    }
}

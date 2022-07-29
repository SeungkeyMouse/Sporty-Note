package com.sportynote.server.repository;

import com.sportynote.server.Enum.NodeType;
import com.sportynote.server.domain.NodeLocationSet;
import com.sportynote.server.domain.NoteNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class NodeLocationSetRepository {
    private final EntityManager em;

    public void save(NodeLocationSet ns) {
        em.persist(ns);
    }

    public Optional<NodeLocationSet> findByMachineIdAndNodeType(Long machineId, NodeType nodeType){
        Optional<NodeLocationSet> optNLS = null;
        try{
            optNLS = Optional.ofNullable(em.createQuery("select ns from NodeLocationSet ns "+
                            "where ns.machine.idx =: machineId " +
                            "and ns.nodeType =: nodeType"
                            , NodeLocationSet.class)
                    .setParameter("machineId", machineId)
                    .setParameter("nodeType", nodeType)
                    .getSingleResult());
        }catch (NoResultException e){
            optNLS = Optional.empty();
        }finally {
            return optNLS;
        }}
}

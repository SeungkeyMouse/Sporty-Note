package com.sportynote.server.repository;

import com.sportynote.server.domain.Note;
import com.sportynote.server.domain.NoteNode;
import com.sportynote.server.domain.UserBasic;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class NoteNodeRepository{
    private final EntityManager em;

    public void save(NoteNode node) {
        em.persist(node);
    }
    public NoteNode findById(Long nodeId){
        return em.createQuery("select n from NoteNode n where n.idx =: nodeId", NoteNode.class)
                .setParameter("nodeId", nodeId)
                .getSingleResult();

    }

}

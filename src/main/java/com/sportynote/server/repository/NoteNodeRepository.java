package com.sportynote.server.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sportynote.server.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class NoteNodeRepository{
    private final EntityManager em;
    private final JPAQueryFactory jpaQueryFactory;
    QNoteNode qNoteNode = new QNoteNode("m");

    public void save(NoteNode node) {
        em.persist(node);
    }

    public Long delete(NoteNode node) {
        em.remove(node);
        return node.getNote().getIdx();
    }
    public NoteNode findById(Long nodeId){
        return em.createQuery("select n from NoteNode n where n.idx =: nodeId", NoteNode.class)
                .setParameter("nodeId", nodeId)
                .getSingleResult();

    }

    public List<NoteNode> findByNoteId(Long noteIdx){
        return jpaQueryFactory.select(qNoteNode).from(qNoteNode).where(qNoteNode.note.idx.eq(noteIdx)).fetch();
    }

}

package com.sportynote.server.repository;

import com.sportynote.server.domain.Gym;
import com.sportynote.server.domain.Record;
import com.sportynote.server.domain.Routine;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class RecordRepository {
    private final EntityManager em;

    public void save(Record record) {
        em.persist(record);
    }

    public List<Routine> findByUserId(String idx, String userid){
        return em.createQuery("select m from Routine m where m.idx=:idx and m.userid=:userid",Routine.class)
                .setParameter("idx",idx).setParameter("userid",userid).getResultList();
    }
}

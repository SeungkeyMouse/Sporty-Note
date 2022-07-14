package com.sportynote.server.repository;

import com.sportynote.server.domain.Routine;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
@Repository
@RequiredArgsConstructor
public class RoutineRepository {
    private final EntityManager em;

    public void save(Routine routine) {
        em.persist(routine);
    }


    public List<Routine> findAll() {
        return em.createQuery("select m from Routine m", Routine.class)
                .getResultList();
    }

    public List<Routine> findByUserId(String idx,String userid){
        return em.createQuery("select m from Routine m where m.idx=:idx and m.userid=:userid",Routine.class)
                .setParameter("idx",idx).setParameter("userid",userid).getResultList();
    }

//    public List<Routine> addRoutine(String userId, Routine routine){
//    }

}

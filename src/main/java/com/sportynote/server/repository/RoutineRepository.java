package com.sportynote.server.repository;

import com.sportynote.server.domain.Machine;
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

    public void save(Machine machine) {
        em.persist(machine);
    }

    public List<Machine> findAll() {
        return em.createQuery("select m from Machine m", Machine.class)
                .getResultList();
    }
}

package com.sportynote.server.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MachineRepository{
    private final EntityManager em;

    public void save(Machine machine) {
        em.persist(machine);
    }

    public List<Machine> findAll() {
        return em.createQuery("select m from Machine m", Machine.class)
                .getResultList();
    }

    public Machine findById(Integer machineId){
        return em.find(Machine.class, machineId);
    }
}
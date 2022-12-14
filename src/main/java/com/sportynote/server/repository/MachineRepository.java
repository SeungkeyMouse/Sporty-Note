package com.sportynote.server.repository;

import com.sportynote.server.domain.Machine;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.crypto.Mac;
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

    public Machine findById(Long machineId) {
        return em.find(Machine.class, machineId);
    }

    public List<Machine> findByName(String machineName) {
        return em.createQuery("select m from Machine m where m.krMachineName =: machineName", Machine.class)
                .setParameter("machineName", machineName)
                .getResultList();
    }
}
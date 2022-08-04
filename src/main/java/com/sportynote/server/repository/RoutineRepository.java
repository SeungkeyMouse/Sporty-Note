package com.sportynote.server.repository;

import com.sportynote.server.domain.Machine;
import com.sportynote.server.domain.Note;
import com.sportynote.server.domain.Routine;

import com.sportynote.server.domain.UserFavorite;
import com.sportynote.server.repository.query.MachineDto;
import com.sportynote.server.repository.query.RoutineDto;
import com.sportynote.server.repository.repositoryImpl.RoutineRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RoutineRepository {
    private final EntityManager em;

    public Routine save(Routine routine) {
        em.persist(routine);
        return routine;
    }

    public void deleteMachine(Long idx){
        em.remove(em.find(Routine.class, idx));
    }


    public List<Routine> findAll() {
        return em.createQuery("select m from Routine m", Routine.class)
                .getResultList();
    }

    public List<Routine> findByUserId(String userid){
        return em.createQuery("select m from Routine m where m.userBasic.userId=:userid",Routine.class)
                .setParameter("userid",userid).getResultList();
    }

    public List<Routine> findByIdAndRoutineName(String userid, String RoutineName){
        return em.createQuery("select m from Routine m"
                        + " join fetch m.machine"
                        + " join fetch m.userBasic where m.userBasic.userId=:userid and m.routineName=:RoutineName",Routine.class)
                .setParameter("userid",userid).setParameter("RoutineName",RoutineName).getResultList();
    }

    public Routine findByUserIdAndMachineIdAndRoutineName(String userId, Long machineId, String routineName) {
            return em.createQuery("select m from Routine m where m.userBasic.userId=:userid and m.machine.idx=:machineId and m.routineName=:routineName", Routine.class)
                    .setParameter("userid", userId).setParameter("machineId",machineId).setParameter("routineName", routineName).getSingleResult();

    }
//    public List<Routine> addRoutine(String userId, Routine routine){
//    }

}

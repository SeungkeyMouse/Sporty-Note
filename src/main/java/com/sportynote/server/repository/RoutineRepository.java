package com.sportynote.server.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sportynote.server.domain.*;

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

    public void deleteMachine(Long idx){
        em.remove(em.find(Routine.class, idx));
    }

    public List<Routine> findAll() {
        return em.createQuery("select m from Routine m", Routine.class)
                .getResultList();
    }
    public void routineNameUpdate(String routineName,String newRoutineName,String userId){
        em.createQuery("update Routine m set m.routineName =:newRoutineName where m.userBasic.userId=:userId and m.routineName=:routineName and m.deleted = false")
                .setParameter("routineName",routineName).setParameter("newRoutineName",newRoutineName)
                .setParameter("userId",userId).executeUpdate();
    }

    public List<Routine> findByUserId(String userId){
        return em.createQuery("select m from Routine m where m.userBasic.userId=:userId",Routine.class)
                .setParameter("userId",userId).getResultList();
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

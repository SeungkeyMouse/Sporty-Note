package com.sportynote.server.repository;

import com.sportynote.server.domain.*;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RoutineRepository {
    private final EntityManager em;
    public void save(Routine routine) {
        em.persist(routine);
    }

    public void deleteRoutineList(Long idx){
        em.remove(em.find(RoutineList.class, idx));
    }
    public void deleteRoutine(Long idx){
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

    public List<RoutineList> findByRoutineListById(Long routineIdx){
        return em.createQuery("select m from RoutineList m where m.routine.idx=:routineIdx",RoutineList.class)
                .setParameter("routineIdx",routineIdx).getResultList();
    }

    public List<RoutineList> findByIdAndRoutineList(Long routineIdx){
        return em.createQuery("select m from RoutineList m"
                        + " join fetch m.machine" + " join fetch m.routine"
                        + " where m.routine.idx=:routineIdx",RoutineList.class)
                .setParameter("routineIdx",routineIdx).getResultList();
    }

    public Optional<Routine> findByIdAndRoutineName(String userid, String routineName) {
        try {
            return Optional.ofNullable(em.createQuery("select m from Routine m"
                            + " join fetch m.userBasic where m.userBasic.userId=:userid and m.routineName=:routineName", Routine.class)
                    .setParameter("userid", userid).setParameter("routineName", routineName).getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public RoutineList findByMachineIdAndRoutineId(Long machineId, Long routineId) {
            return em.createQuery("select m from RoutineList m"+
                            " where m.machine.idx=:machineId and m.routine.idx=:routineId", RoutineList.class)
                    .setParameter("machineId",machineId)
                    .setParameter("routineId", routineId)
                    .getSingleResult();
    }


//    public List<Routine> addRoutine(String userId, Routine routine){
//    }

}

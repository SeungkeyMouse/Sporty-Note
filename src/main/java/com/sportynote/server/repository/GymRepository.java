package com.sportynote.server.repository;

import com.sportynote.server.domain.Gym;
import com.sportynote.server.repository.dto.GymDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
@Repository
@RequiredArgsConstructor
public class GymRepository {
    private final EntityManager em;
    public void save(Gym gym) { em. persist(gym);}

    public List<Gym> findAll(){
        return em.createQuery("select g from Gym g", Gym.class)
                .getResultList();
    }
}

package com.sportynote.server.repository;

import com.sportynote.server.domain.Gym;
import com.sportynote.server.domain.Machine;
import com.sportynote.server.domain.UserBasic;
import com.sportynote.server.domain.UserFavorite;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserBasicRepository {
    private final EntityManager em;

    public void save(UserBasic userBasic) {
        em.persist(userBasic);
    }

    public List<UserBasic> findAll() {
        return em.createQuery("select u from UserBasic u", UserBasic.class)
                .getResultList();
    }

    public UserBasic findById(String userId){
        return em.createQuery("select u from UserBasic u where u.userId =: userId", UserBasic.class)
                .setParameter("userId", userId)
                .getSingleResult();

    }
}

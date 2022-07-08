package com.sportynote.server.repository;

import com.sportynote.server.domain.UserBasic;
import com.sportynote.server.domain.UserFavorite;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserFavoriteRepository {
    private final EntityManager em;

    public void save(UserFavorite userFavorite) {
        em.persist(userFavorite);
    }

    public List<UserFavorite> findAll() {
        return em.createQuery("select uf from UserFavorite uf", UserFavorite.class)
                .getResultList();
    }

    public UserFavorite findById(Integer userFavoriteIdx){
        return em.find(UserFavorite.class, userFavoriteIdx);
    }
}

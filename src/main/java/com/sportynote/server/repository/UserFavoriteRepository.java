package com.sportynote.server.repository;

import com.sportynote.server.domain.UserBasic;
import com.sportynote.server.domain.UserFavorite;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserFavoriteRepository {
    private final EntityManager em;

    public void save(UserFavorite userFavorite) {
        em.persist(userFavorite);
    }

    public Long delete(UserFavorite userFavorite) {//제거
        em.remove(userFavorite);
        return userFavorite.getIdx();
    }
    public List<UserFavorite> findAll() {
        return em.createQuery("select uf from UserFavorite uf", UserFavorite.class)
                .getResultList();
    }
    public List<UserFavorite> findAllByUserId(String userId) {
        return em.createQuery("select uf from UserFavorite uf " +
                        "where uf.userBasic.userId=: userId and uf.deleted = false", UserFavorite.class)
                .setParameter("userId",userId)
                .getResultList();
    }
    public UserFavorite findById(Long userFavoriteIdx) {
        return em.find(UserFavorite.class, userFavoriteIdx);
    }

    public Optional<UserFavorite> findByUserId(String userId) {
        Optional<UserFavorite> userFavorite = null;
        try {
            userFavorite = Optional.ofNullable(em.createQuery("select uf from UserFavorite uf where uf.userBasic.userId =: userId", UserFavorite.class)
                    .setParameter("userId", userId)
                    .getSingleResult());
        } catch (NoResultException e) {
            userFavorite = Optional.empty();
        } finally {
            return userFavorite;
        }

    }

    public Optional<UserFavorite> findByUserIdAndMachineId(String userId, Long machineId) {
        Optional<UserFavorite> userFavorite = null;
        try {
            userFavorite = Optional.ofNullable(
                    em.createQuery(
                                    "select uf from UserFavorite uf where uf.userBasic.userId =: userId " +
                                            "and uf.machine.idx =: machineId", UserFavorite.class)
                            .setParameter("userId", userId)
                            .setParameter("machineId", machineId)
                            .getSingleResult());
        } catch (NoResultException e) {
            userFavorite = Optional.empty();
        } finally {
            return userFavorite;
        }
    }
}

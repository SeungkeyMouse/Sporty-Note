package com.sportynote.server.repository;

import com.sportynote.server.Enum.SocialType;
import com.sportynote.server.domain.*;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserBasicRepository {
    private final EntityManager em;

    @Transactional
    public void save(UserBasic userBasic) {
        em.persist(userBasic);
    }

    public List<UserBasic> findAll() {
        return em.createQuery("select u from UserBasic u", UserBasic.class)
                .getResultList();
    }
    public void delete(Long idx){
        em.remove(em.find(UserBasic.class, idx));
    }

    public UserBasic findById(String userId){
        return em.createQuery("select u from UserBasic u where u.userId =: userId", UserBasic.class)
                .setParameter("userId", userId)
                .getSingleResult();
    }
    public UserBasic findByOauthId(String oauthId){
        return em.createQuery("select u from UserBasic u where u.oauthId =: oauthId", UserBasic.class)
                .setParameter("oauthId", oauthId)
                .getSingleResult();
    }

    public Optional<UserBasic> existsByIdAndSocialType(String oauthId, SocialType socialType){
        return em.createQuery("select u from UserBasic u where u.oauthId=:oauthId and u.socialType=:socialType",UserBasic.class)
                .setParameter("oauthId",oauthId)
                .setParameter("socialType",socialType)
                .getResultList().stream().findAny();
    }
}

package com.sportynote.server.repository;

import com.sportynote.server.Enum.SocialType;
<<<<<<< HEAD
import com.sportynote.server.domain.*;
=======
import com.sportynote.server.domain.Gym;
import com.sportynote.server.domain.Machine;
import com.sportynote.server.domain.UserBasic;
import com.sportynote.server.domain.UserFavorite;
>>>>>>> e356767f084accefdb147c73c6a441ef3fdb504d
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
<<<<<<< HEAD
    public void delete(Long idx){
        em.remove(em.find(UserBasic.class, idx));
    }
=======
>>>>>>> e356767f084accefdb147c73c6a441ef3fdb504d

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

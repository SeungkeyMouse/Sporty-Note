package com.sportynote.server.repository.repositoryImpl;

import com.sportynote.server.type.SocialType;
import com.sportynote.server.domain.UserBasic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBasicRepositoryImpl extends JpaRepository<UserBasic,Long> {
    boolean existsByOauthIdAndSocialType(String oauthId,SocialType socialType);
}

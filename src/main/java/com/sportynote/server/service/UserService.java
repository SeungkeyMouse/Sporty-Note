package com.sportynote.server.service;

import com.sportynote.server.domain.Routine;
import com.sportynote.server.domain.UserBasic;
import com.sportynote.server.repository.UserBasicRepository;
import com.sportynote.server.security.JwtTokenProvider;
import io.jsonwebtoken.Jwt;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class UserService {
    UserBasicRepository userBasicRepository;
    JwtTokenProvider jwtTokenProvider;

    public UserService(UserBasicRepository userBasicRepository, JwtTokenProvider jwtTokenProvider) {
        this.userBasicRepository = userBasicRepository;
        this.jwtTokenProvider=jwtTokenProvider;
    }

    /** 루틴 하나 삭제 */
    public boolean deleteUsers(String jwtToken) {
        if (jwtTokenProvider.validateToken(jwtToken)) {
            UserBasic userBasic = userBasicRepository.findById(jwtTokenProvider.getTokenToUserId(jwtToken));
            userBasicRepository.delete(userBasic.getIdx());
            return true;
        }
        return false;
    }
}

package com.sportynote.server.service;

import com.sportynote.server.domain.Routine;
import com.sportynote.server.domain.UserBasic;
import com.sportynote.server.repository.UserBasicRepository;
import com.sportynote.server.security.JwtTokenProvider;
import com.sportynote.server.util.RedisUtil;
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
    RedisUtil redisUtil;
    public UserService(UserBasicRepository userBasicRepository, JwtTokenProvider jwtTokenProvider,RedisUtil redisUtil) {
        this.userBasicRepository = userBasicRepository;
        this.jwtTokenProvider=jwtTokenProvider;
        this.redisUtil=redisUtil;
    }

    /**
     * 한 회원이 회원탈퇴를 누르게 될 경우 해당 유저를 삭제하는 함수 redis 로그아웃 포함
     * @param userId,jwtToken
     * @return
     */
    public boolean deleteUser(String userId,String jwtToken) {
        UserBasic userBasic = userBasicRepository.findById(userId);
        userBasicRepository.delete(userBasic.getIdx());
        System.out.println(jwtToken);
        return redisUtil.delete(jwtToken);
    }

}

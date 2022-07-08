package com.sportynote.server.service;

import com.sportynote.server.domain.Machine;
import com.sportynote.server.domain.UserBasic;
import com.sportynote.server.domain.UserFavorite;
import com.sportynote.server.repository.MachineRepository;
import com.sportynote.server.repository.UserBasicRepository;
import com.sportynote.server.repository.UserFavoriteRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.Mac;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MachineService {

    private final MachineRepository machineRepository;
    private final UserBasicRepository userBasicRepository;
    private final UserFavoriteRepository userFavoriteRepository;
    /**
     * 즐겨찾기 추가
     */
    @Transactional
    public Integer addFavorite(String userId, Integer machineId) {

        //엔티티 조회
        UserBasic userBasic = userBasicRepository.findById(userId);
        Machine machine = machineRepository.findById(machineId);

        //즐겨찾기 생성
        UserFavorite userFavorite = UserFavorite.createFavorite(userBasic, machine);

        //주문 저장
        userFavoriteRepository.save(userFavorite);

        return userFavorite.getIdx();
    }

}

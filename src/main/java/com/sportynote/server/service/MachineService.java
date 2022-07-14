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
import java.util.List;
import java.util.Optional;

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
        System.out.println(userId +" " +String.valueOf(machineId));
        Optional<UserFavorite> userFavorites = userFavoriteRepository.findByUserId(userId);

        //엔티티 조회
        System.out.println("gs25");
        UserBasic userBasic = userBasicRepository.findById(userId);
        System.out.println("userBasicgetidx:"+userBasic.getIdx());
        System.out.println("TEST1");
        Machine machine = machineRepository.findById(machineId);
        System.out.println("TEST2");
        //case1 : 처음 즐겨찾기를 추가한다면=> favoriteMachines 리스트가 비어있음.
        if(!userFavorites.isPresent()){
            //즐겨찾기 생성
            UserFavorite userFavorite = UserFavorite.createFavorite(userBasic, machine);

            //주문 저장
            userFavoriteRepository.save(userFavorite);

            return userFavorite.getIdx();
        }

        //case2 : 처음이 아니라 두번째이상에서 추가한다면=>favoriteMachines 리스트가 한개이상 들어있음.
        UserFavorite userFavorite = userFavorites.get();
        userFavorite.addFavoriteMachine(machine);

        userFavoriteRepository.save(userFavorite);
        return userFavorite.getIdx();
    }

}

package com.sportynote.server.service;

import com.sportynote.server.domain.Machine;
import com.sportynote.server.domain.UserBasic;
import com.sportynote.server.domain.UserFavorite;
import com.sportynote.server.repository.MachineRepository;
import com.sportynote.server.repository.UserBasicRepository;
import com.sportynote.server.repository.UserFavoriteRepository;
import com.sportynote.server.repository.query.MachineDto;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.Mac;
import java.util.LinkedList;
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
        Optional<UserFavorite> userFavorites = userFavoriteRepository.findByUserIdAndMachineId(userId, machineId);
        //엔티티 조회
        UserBasic userBasic = userBasicRepository.findById(userId);
        Machine machine = machineRepository.findById(machineId);
        //case1 : 해당 기구가 즐겨찾기 목록에 없다면 => UserFavorite 생성해서 기구 매핑
        if(!userFavorites.isPresent()) {
            //즐겨찾기 생성
            UserFavorite userFavorite = UserFavorite.createFavorite(userBasic, machine);

            //주문 저장
            userFavoriteRepository.save(userFavorite);

            return userFavorite.getIdx();
        }
        //case2 : 이미 등록되어 있으면 즐겨찾기에서 기구 제거
        return userFavoriteRepository.delete(userFavorites.get());
    }

    /**
     * 즐겨찾기 출력
     * @param userId : 전체 출력 해결되면, 한명의 즐겨찾기 기구들만 조회
     * @return 기구 리스트
     */
    public List<MachineDto> getFavorite(String userId) {
        List<MachineDto> favoriteMachines = new LinkedList<>();

        List<UserFavorite> userFavorites = userFavoriteRepository.findAllByUserId(userId);

        if(!userFavorites.isEmpty()){
            //즐겨찾기한 데이터가 있으면 모두 반환.
            for (UserFavorite userFavorite : userFavorites) {
                Machine sampleM = userFavorite.getMachine();
                MachineDto machineDto = new MachineDto(
                        sampleM.getIdx(),
                        sampleM.getMachineName(),
                        sampleM.getTargetArea(),
                        sampleM.getUrl()
                );
                favoriteMachines.add(machineDto);
            }
            return favoriteMachines;
        }

        return favoriteMachines;
    }
}

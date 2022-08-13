package com.sportynote.server.service;

import com.sportynote.server.Enum.NodeType;
import com.sportynote.server.domain.*;
import com.sportynote.server.repository.MachineRepository;
import com.sportynote.server.repository.NodeLocationSetRepository;
import com.sportynote.server.repository.UserBasicRepository;
import com.sportynote.server.repository.UserFavoriteRepository;
import com.sportynote.server.repository.query.GymDto;
import com.sportynote.server.repository.query.MachineDto;
import com.sportynote.server.repository.query.NodeLocationDto;
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

    private final NodeLocationSetRepository nodeLocationSetRepository;
    /**
     * 머신 저장
     * */
    public void save(MachineDto machineDto) {
        Machine machine = new Machine();

        machine.setKrMachineName(machineDto.getKrMachineName());
        machine.setEngMachineName(machineDto.getEngMachineName());
        machine.setTargetArea(machineDto.getTargetArea());
        machine.setUrl(machineDto.getUrl());
        machineRepository.save(machine);
    }

    /**
     * 즐겨찾기 추가
     */
    @Transactional
    public Long addFavorite(String userId, Long machineId) {
        Optional<UserFavorite> userFavorites = userFavoriteRepository.findByUserIdAndMachineId(userId, machineId);
        //엔티티 조회
        UserBasic userBasic = userBasicRepository.findById(userId);
        Machine machine = machineRepository.findById(machineId);
        //case1 : 해당 기구가 즐겨찾기 목록에 없다면 => UserFavorite 생성해서 기구 매핑
        if(userFavorites.isEmpty()) {
            //즐겨찾기 생성
            UserFavorite userFavorite = UserFavorite.createFavorite(userBasic, machine);

            //주문 저장
            userFavoriteRepository.save(userFavorite);

            return userFavorite.getIdx();
        }
        //case2 : 이미 등록되어 있으면
        UserFavorite uf = userFavorites.get();
        uf.setDeleted(false);
        return uf.getIdx();
    }
    @Transactional
    public Long deleteFavorite(Long userFavoriteIdx){
        UserFavorite uf = userFavoriteRepository.findById(userFavoriteIdx);
        return userFavoriteRepository.delete(uf);
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
                Machine favoritedMachine = userFavorite.getMachine();
                MachineDto machineDto = new MachineDto(
                        favoritedMachine.getIdx(),
                        favoritedMachine.getKrMachineName(),
                        favoritedMachine.getEngMachineName(),
                        favoritedMachine.getTargetArea(),
                        favoritedMachine.getUrl(),
                        userFavorite.getIdx()
                );
                favoriteMachines.add(machineDto);
            }
            return favoriteMachines;
        }

        return favoriteMachines;
    }

    @Transactional
    public String addNodeLocation(NodeLocationDto nodeLocationDto) {
        try {
            Optional<NodeLocationSet> optNLS= nodeLocationSetRepository.findByMachineIdAndNodeType(
                    nodeLocationDto.getMachineId(),
                    NodeType.findNodeType(nodeLocationDto.getNodeType().getEngName())
            );
            //세팅되어있는 부위의 위치가 있다면 해당 값을 집어넣음
            if(!optNLS.isEmpty()){
                NodeLocationSet nls = optNLS.get();
                nls.setX_location(nodeLocationDto.getX_location());
                nls.setY_location(nodeLocationDto.getY_location());
            }
            else {
                Machine machine = machineRepository.findById(nodeLocationDto.getMachineId());
                NodeLocationSet nodeLocationSet = NodeLocationSet.createNodeLocationSet(machine, nodeLocationDto);
                nodeLocationSetRepository.save(nodeLocationSet);
            }
        }catch (Exception e){
            return "Fail";
        }

        return "Success";
    }
}
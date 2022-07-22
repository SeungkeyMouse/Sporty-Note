package com.sportynote.server.service;

import com.sportynote.server.domain.*;
import com.sportynote.server.repository.MachineRepository;
import com.sportynote.server.repository.RoutineRepository;
import com.sportynote.server.repository.UserBasicRepository;
import com.sportynote.server.repository.UserFavoriteRepository;
import com.sportynote.server.repository.query.MachineDto.RoutineMachineDto;
import com.sportynote.server.repository.query.RoutineDto.MachineRoutineDto;
import com.sportynote.server.repository.query.*;
import com.sportynote.server.repository.repositoryImpl.RoutineRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.*;


@Service
@Transactional
@RequiredArgsConstructor
public class RoutineService {
    private final RoutineRepository routineRepository;
    private final MachineRepository machineRepository;
    private final UserBasicRepository userBasicRepository;
    private final RoutineRepositoryImpl routineRepositoryImpl;

    /**
     * 루틴 하나 저장
     */
//    public void save(RoutineDto routineDto) {
//        Routine routine = new Routine();
//
//        routine.setRoutineName(routineDto.getMachineName());
//        machine.setTargetArea(machineDto.getTargetArea());
//        machine.setUrl(machineDto.getUrl());
//        machineRepository.save(machine);
//    }

    /**
     루틴 하나 추가 CREATE
     */
    public boolean addRoutine(RoutineDto routineDto) {
            List<Integer> machines = routineDto.getMachines();
            List<Routine> routineList = new ArrayList<>();
            UserBasic userBasicInfo = userBasicRepository.findById("123123"); //예시 userid
            for (Integer integer : machines) {
                Machine machineInfo = machineRepository.findById(integer);
                Routine routine = Routine.createRoutine(routineDto.getRoutineName(),userBasicInfo ,machineInfo);
                routineList.add(routine);
            }
            routineRepositoryImpl.saveAll(routineList);
            return true;
    }

    /**
     * 내 루틴 조회 Read
     */
    public Set<String> myRoutine(String userid) {
        List<Routine> RoutineLists = routineRepository.findByUserId(userid);
        Set<String> RoutineNames = new TreeSet<>();
        for(Routine routine : RoutineLists){
            RoutineNames.add(routine.getRoutineName());
        }
        return RoutineNames;
    }

    /**
     * 내 루틴 내 기구 조회
     */
    public List<RoutineMachineDto> findByIdAndRoutineName(String RoutineName){
        String userid="123123";
        List<Routine> RoutineLists = routineRepository.findByIdAndRoutineName(userid, RoutineName);
        List<RoutineMachineDto> MachineLists = new ArrayList<>();
        for(Routine routine : RoutineLists){
            System.out.println(routine.getMachine().getIdx()+routine.getMachine().getUrl());
            MachineLists.add(new RoutineMachineDto(routine.getIdx(),routine.getMachine().getIdx(),routine.getMachine().getMachineName(),routine.getMachine().getTargetArea(),routine.getMachine().getUrl()));
        }
        return MachineLists;
    }

    /**
     루틴 하나 수정 UPDATE
     */
    public boolean modifyRoutine(MachineRoutineDto machineRoutineDto) {
        Routine routine = new Routine();
        /**{
            "machines": [
            6,7,8
            ],
            "routineName": "routine",
                "userid": "123123"
        }*/
        List<Integer> machines = machineRoutineDto.getMachines(); // 6,7,8
        List<Integer> machineList = new ArrayList<>();
        UserBasic userBasicInfo = userBasicRepository.findById("123123"); //예시 userid

        /** 원래 가지고 있던 루틴 리스트의 머신들을 가져옴*/
        List<Routine> routineList = routineRepository.findByIdAndRoutineName("123123", machineRoutineDto.getRoutineName());
        for(int i =0;i<routineList.size();i++){
            machineList.add(routineList.get(i).getMachine().getIdx()); /**Integer machine_id 만 가져옴*/
        }

        for (Integer integer : machines) {
            Machine machineInfo = machineRepository.findById(integer);

            Optional<Routine> optRoutine = routineRepository.findByUserIdAndMachineIdAndRoutineName
                    (machineRoutineDto.getRoutineName(), machineInfo.getIdx(), userBasicInfo.getUserId());

            if (!optRoutine.isPresent()) { /**내가 값을 보냈고 만약 루틴이 존재할 경우 */ /** 그대로 덮어씀 */
                routine = optRoutine.get();
            } else { /**내가 값을 보냈고 만약 루틴이 존재하지 않을 경우 */ /** 새로 추가함 */ /** 루틴 추가 함수화 예정 */
                routine = Routine.createRoutine(machineRoutineDto.getRoutineName(), userBasicInfo, machineInfo);
            }
            routineRepository.save(routine);
        }
        for (int i=0;i<machineList.size();i++){
            /**DELETE 구문은 다음주에 스크럼 및 코드 리팩토링 후 구현...
             * routineRepository.findByIdAndRoutineName(machineList.get(i))*/
        }

        return true;
    }

}

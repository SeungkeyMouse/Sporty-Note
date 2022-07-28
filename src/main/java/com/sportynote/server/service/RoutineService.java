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

//    private final RoutineService routineService;
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
     루틴 하나(여러 기구)첫 추가 CREATE
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
    public boolean modifyRoutine(RoutineDto routineDto){
        List<Routine> existRoutine = routineRepository.findByIdAndRoutineName(routineDto.getUserid(), routineDto.getRoutineName());
        HashSet<Integer> dtoSet = new HashSet<>(); /** 루틴 요청 저장할 기구 리스트*/
        HashSet<Integer> existSet = new HashSet<>();
        List<Integer> updateLists = new ArrayList<>();
        List<Integer> deleteLists = new ArrayList<>();
        /** HashSet 설정 */
        for(int i=0;i<routineDto.getMachines().size();i++){
            dtoSet.add(routineDto.getMachines().get(i));
        }
        for(int i=0;i<existRoutine.size();i++){
            existSet.add(existRoutine.get(i).getMachine().getIdx());
        }

//        for(int i=0;i<routineDto.getMachines().size();i++){
//            System.out.print(dtoSet.)
//        }
        for(int i=0;i<existRoutine.size();i++){
            existSet.add(existRoutine.get(i).getMachine().getIdx());
        }
        /** existRoutine - dtoLists ,  routineDto - existLists */
        for(int i =0;i<dtoSet.size();i++) {
            System.out.print(routineDto.getMachines().get(i)+" ");
            if(!existSet.remove(routineDto.getMachines().get(i))){
                System.out.print("updateList : "+ i);
                updateLists.add(routineDto.getMachines().get(i));
            }
        }
        System.out.println("");
        for(int i =0;i<existSet.size();i++){
            System.out.print(existRoutine.get(i).getMachine().getIdx()+" ");
            if(!dtoSet.remove(existRoutine.get(i).getMachine().getIdx())){
                deleteLists.add(existRoutine.get(i).getMachine().getIdx());
            }
        }
        System.out.println(updateLists.size());
        System.out.println(deleteLists.size());
        List<Routine> routineList = new ArrayList<>();
        UserBasic userBasic = userBasicRepository.findById(routineDto.getUserid()); //예시 userid
        /** UPDATE 중 SAVE */
        updateRoutineList(routineDto.getRoutineName(),userBasic,updateLists);

        /** UPDATE 중 DELETE */
        deleteRoutineList(routineDto.getRoutineName(),userBasic,deleteLists);

        return true;
    }

    /** 루틴 기구만 업데이트 */
    public boolean updateRoutineList(String routineName, UserBasic userBasic,List<Integer> machines) {
        List<Routine> routineList = new ArrayList<>();
        for (Integer integer : machines) {
            System.out.println("updateRoutineList : " + integer);
            Machine machineInfo = machineRepository.findById(integer);
            Routine routine = Routine.createRoutine(routineName,userBasic ,machineInfo);
            routineList.add(routine);
        }
        routineRepositoryImpl.saveAll(routineList);
        return true;
    }

    /** 루틴 기구만 삭제 */
    public boolean deleteRoutineList(String routineName, UserBasic userBasic, List<Integer> machines) {
        for (Integer integer : machines) {
            Routine routine = routineRepository.findByUserIdAndMachineIdAndRoutineName(userBasic.getUserId(),integer,routineName);
            System.out.println("deleteRoutineList : " + integer);
            routineRepository.delete(routine.getIdx());
        }
        return true;
    }
    /**
    public boolean modifyRoutine(MachineRoutineDto machineRoutineDto) {
        Routine routine = new Routine(); //
        /**{
            "machines": [
            6,7,8
            ],
            "routineName": "routine",
                "userid": "123123"
        }
        List<Integer> machines = machineRoutineDto.getMachines(); // 6,7,8
        List<Integer> machineList = new ArrayList<>();
        UserBasic userBasicInfo = userBasicRepository.findById("123123"); //예시 userid

        /** 원래 가지고 있던 루틴 리스트의 머신들을 가져옴
        List<Routine> routineList = routineRepository.findByIdAndRoutineName("123123", machineRoutineDto.getRoutineName());
        for(int i =0;i<routineList.size();i++){
            machineList.add(routineList.get(i).getMachine().getIdx()); /**Integer machine_id 만 가져옴
        }

        for (Integer integer : machines) {
            Machine machineInfo = machineRepository.findById(integer);

            Optional<Routine> optRoutine = routineRepository.findByUserIdAndMachineIdAndRoutineName
                    (machineRoutineDto.getRoutineName(), machineInfo.getIdx(), userBasicInfo.getUserId());

            if (!optRoutine.isPresent()) { /**내가 값을 보냈고 만약 루틴이 존재할 경우  /** 그대로 덮어씀
                routine = optRoutine.get();
            } else { /**내가 값을 보냈고 만약 루틴이 존재하지 않을 경우 /** 새로 추가함  /** 루틴 추가 함수화 예정
                routine = Routine.createRoutine(machineRoutineDto.getRoutineName(), userBasicInfo, machineInfo);
            }
            routineRepository.save(routine);
        }
        for (int i=0;i<machineList.size();i++){
            /**DELETE 구문은 다음주에 스크럼 및 코드 리팩토링 후 구현...
             * routineRepository.findByIdAndRoutineName(machineList.get(i))
        }

        return true;
    }*/

}


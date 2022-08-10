package com.sportynote.server.service;

import com.sportynote.server.domain.*;
import com.sportynote.server.repository.MachineRepository;
import com.sportynote.server.repository.RoutineRepository;
import com.sportynote.server.repository.UserBasicRepository;
import com.sportynote.server.repository.UserFavoriteRepository;
import com.sportynote.server.repository.query.RoutineMachineDto;
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

    /** 루틴 하나(여러 기구)첫 추가 CREATE */
    public boolean addRoutine(RoutineDto routineDto) {
            List<Long> machines = routineDto.getMachines();
            List<Routine> routineList = new ArrayList<>();
            UserBasic userBasicInfo = userBasicRepository.findById("12312312"); //예시 userid
            for (Long machine : machines) {
                Machine machineInfo = machineRepository.findById(machine);
                Routine routine = Routine.createRoutine(routineDto.getRoutineName(),userBasicInfo ,machineInfo);
                routineList.add(routine);
            }
            routineRepositoryImpl.saveAll(routineList);
            return true;
    }

    /** 내 루틴 조회 Read */
    public Set<String> myRoutine(String userid) {
        List<Routine> RoutineLists = routineRepository.findByUserId(userid);
        Set<String> RoutineNames = new TreeSet<>();
        for(Routine routine : RoutineLists){
            RoutineNames.add(routine.getRoutineName());
        }
        return RoutineNames;
    }

    /** 내 루틴 내 기구 조회 */
    public List<RoutineMachineDto> findByIdAndRoutineName(String RoutineName){
        String userid="123123";
        List<Routine> RoutineLists = routineRepository.findByIdAndRoutineName(userid, RoutineName);
        List<RoutineMachineDto> MachineLists = new ArrayList<>();
        for(Routine routine : RoutineLists){
            MachineLists.add(new RoutineMachineDto(routine.getIdx(),routine.getMachine().getIdx(),routine.getMachine().getKrMachineName(),routine.getMachine().getTargetArea(),routine.getMachine().getUrl()));
        }
        return MachineLists;
    }

    /** 루틴 하나 수정 UPDATE */
    public boolean modifyRoutine(RoutineDto routineDto){
        List<Routine> routineExist = routineRepository.findByIdAndRoutineName(routineDto.getUserid(), routineDto.getRoutineName());
        HashSet<Long> routineDtoSet = new HashSet<>(); /** 루틴 요청 저장할 기구 리스트*/
        HashSet<Long> routineExistSet = new HashSet<>(); /** 요청할 기구 리스트 */
        HashSet<Long> routineTempSet = new HashSet<>(); /** 차집합을 위한 임시 해시셋 */
        /** HashSet 설정 */
        for(int i=0;i<routineDto.getMachines().size();i++){
            routineDtoSet.add(routineDto.getMachines().get(i));
        }
        for(int i=0;i<routineExist.size();i++){
            routineExistSet.add(routineExist.get(i).getMachine().getIdx());
        }
        routineTempSet.addAll(routineExistSet); // A, A, B   기존, 기존복사, 요청
        routineExistSet.removeAll(routineDtoSet); // A-B, A, B 기존-요청, 기존복사, 요청
        routineDtoSet.removeAll(routineTempSet); //A-B,A ,B-A 기존-요청, 기존복사, 요청-기존복사

        UserBasic userBasic = userBasicRepository.findById(routineDto.getUserid()); //예시 userid
        /** UPDATE 중 SAVE */
        updateRoutineList(routineDto.getRoutineName(),userBasic,routineDtoSet);

        /** UPDATE 중 DELETE */
        deleteRoutineList(routineDto.getRoutineName(),userBasic,routineExistSet);

        return true;
    }

    /** 루틴내 기구만 업데이트 */
    public boolean updateRoutineList(String routineName, UserBasic userBasic,HashSet<Long> routineDtoSet) {
        List<Routine> routineList = new ArrayList<>();
        for (Long machine : routineDtoSet) {
            Machine machineInfo = machineRepository.findById(machine);
            Routine routine = Routine.createRoutine(routineName,userBasic ,machineInfo);
            routineList.add(routine);
        }
        routineRepositoryImpl.saveAll(routineList);
        return true;
    }

    /** 루틴내 기구만 삭제 */
    public boolean deleteRoutineList(String routineName, UserBasic userBasic, HashSet<Long> routineExistSet) {
        for (Long machine : routineExistSet) {
            Routine routine = routineRepository.findByUserIdAndMachineIdAndRoutineName(userBasic.getUserId(),machine,routineName);
            routineRepository.deleteMachine(routine.getIdx());
        }
        return true;
    }

    /** 루틴 하나 삭제 */
    public boolean deleteRoutine(String routineName){
        List<Routine> routineLists = routineRepository.findByIdAndRoutineName("123123",routineName);
        for(Routine routine : routineLists) {
            routineRepository.deleteMachine(routine.getIdx());
        }
        return true;
    }

}


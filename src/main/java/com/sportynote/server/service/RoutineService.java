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

    /** 루틴 하나(여러 기구)첫 추가 CREATE */
    public boolean addRoutine(String userId, RoutineDto routineDto) {
        List<Long> machines = routineDto.getMachines();
        List<Routine> routineList = new ArrayList<>();
        UserBasic userBasicInfo = userBasicRepository.findById(userId);
        List<Routine> routineCheck = routineRepository.findByIdAndRoutineName(userId, routineDto.getRoutineName());
        //사용자의 루틴이름이 없을경우
        if (routineCheck.size()==0) {
            for (Long machine : machines) {
                Machine machineInfo = machineRepository.findById(machine);
                Routine routine = Routine.createRoutine(routineDto.getRoutineName(), userBasicInfo);
                routineList.add(routine);
            }
            routineRepositoryImpl.saveAll(routineList);
            return true;
        }
        return false;
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
    public List<RoutineMachineDto> findByIdAndRoutineName(String userId, String RoutineName){
        List<Routine> RoutineLists = routineRepository.findByIdAndRoutineName(userId, RoutineName);
        List<RoutineMachineDto> MachineLists = new ArrayList<>();
//        for(Routine routine : RoutineLists){
//            MachineLists.add(new RoutineMachineDto(routine.getIdx(),routine.getMachine().getIdx(),routine.getMachine().getKrMachineName(),
//                    routine.getMachine().getTargetArea(),routine.getMachine().getImageUrl1()));
//        }
        return MachineLists;
    }

    /** 루틴 하나 수정 UPDATE */
    public boolean modifyRoutine(String userId, RoutineModifyDto routineModifyDto){
        List<Routine> routineExist = routineRepository.findByIdAndRoutineName(userId, routineModifyDto.getRoutineName());
        //존재하지 않는 루틴일 경우 수정 불가능
        if (routineExist.size()==0) { return false; }

        HashSet<Long> routineDtoSet = new HashSet<>(); /** 루틴 요청 저장할 기구 리스트*/
        HashSet<Long> routineExistSet = new HashSet<>(); /** 요청할 기구 리스트 */
        HashSet<Long> routineTempSet = new HashSet<>(); /** 차집합을 위한 임시 해시셋 */

        /** HashSet 설정 */
        for(int i=0;i<routineModifyDto.getMachines().size();i++){
            routineDtoSet.add(routineModifyDto.getMachines().get(i));
        }
//        for(int i=0;i<routineExist.size();i++){
//            routineExistSet.add(routineExist.get(i).getMachine().getIdx());
//        }
        routineTempSet.addAll(routineExistSet); // A, A, B   기존, 기존복사, 요청
        routineExistSet.removeAll(routineDtoSet); // A-B, A, B 기존-요청, 기존복사, 요청
        routineDtoSet.removeAll(routineTempSet); //A-B,A ,B-A 기존-요청, 기존복사, 요청-기존복사

        UserBasic userBasic = userBasicRepository.findById(userId); //예시 userid

        /** UPDATE 중 SAVE */
        updateRoutineList(routineModifyDto.getNewRoutineName(),userBasic,routineDtoSet);

        /** UPDATE 중 DELETE */
        deleteRoutineList(routineModifyDto.getRoutineName(),userBasic,routineExistSet);

        /** UPDATE 후 루틴에 대한 모든 이름 변경 */
        changeRoutineNameList(routineModifyDto,userBasic);


        return true;
    }
    /** 기존에 있던 기구들에 대한 이름 변경 */
    public void changeRoutineNameList(RoutineModifyDto routineModifyDto,UserBasic userBasic) {
        String routineName = routineModifyDto.getRoutineName();
        String newRoutineName = routineModifyDto.getNewRoutineName();
        routineRepository.routineNameUpdate(routineName,newRoutineName,userBasic.getUserId());
    }

    /** 루틴내 기구만 업데이트 */
    public boolean updateRoutineList(String newRoutineName, UserBasic userBasic,HashSet<Long> routineDtoSet) {
        List<Routine> routineList = new ArrayList<>();
        for (Long machine : routineDtoSet) {
            Machine machineInfo = machineRepository.findById(machine);
            Routine routine = Routine.createRoutine(newRoutineName,userBasic);
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
    public boolean deleteRoutine(String userId, String routineName){
        List<Routine> routineLists = routineRepository.findByIdAndRoutineName(userId,routineName);
        for(Routine routine : routineLists) {
            routineRepository.deleteMachine(routine.getIdx());
        }
        return true;
    }

}


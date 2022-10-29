package com.sportynote.server.service;

import com.sportynote.server.domain.*;
import com.sportynote.server.repository.MachineRepository;
import com.sportynote.server.repository.RoutineRepository;
import com.sportynote.server.repository.UserBasicRepository;
import com.sportynote.server.repository.query.RoutineMachineDto;
import com.sportynote.server.repository.query.*;
import com.sportynote.server.repository.repositoryImpl.RoutineListRepositoryImpl;
import com.sportynote.server.repository.repositoryImpl.RoutineRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
@Transactional
@RequiredArgsConstructor
public class RoutineService {
    private final RoutineRepository routineRepository;
    private final MachineRepository machineRepository;
    private final UserBasicRepository userBasicRepository;
    private final RoutineRepositoryImpl routineRepositoryImpl;
    private final RoutineListRepositoryImpl routineListRepositoryImpl;

    /** 루틴 하나(여러 기구)첫 추가 CREATE */
    @Transactional
    public boolean addRoutine(String userId, RoutineDto routineDto) {
        UserBasic userBasicInfo = userBasicRepository.findById(userId);
        Optional<Routine> routineCheck = routineRepository.findByIdAndRoutineName(userId, routineDto.getRoutineName());
        // 사용자의 루틴이름이 있을경우 false 리턴

        if(!routineCheck.isEmpty()  || routineDto.getMachines().size()==0){
            return false;
        }
        Long idx = addRoutineName(routineDto.getRoutineName(),userBasicInfo);
        return addRoutineList(routineDto.getMachines(),routineDto.getRoutineName(),userBasicInfo,idx);
        //return true;
    }

    /**
     * 루틴 이름과 유저정보를 받아 해당 유저의 루틴 제목을 저장하는 함수
     * @param routineName
     * @param userBasicInfo
     */
    public Long addRoutineName(String routineName,UserBasic userBasicInfo){
        Routine routine = Routine.createRoutine(routineName, userBasicInfo);
        return routineRepositoryImpl.save(routine).getIdx();
        //routineRepository.save(routine);
    }

    /**
     * 유저가 만든 루틴이 있을경우 머신과 루틴을 생성하는 함수
     * @param machines
     * @param userBasicInfo
     */
    public boolean addRoutineList(List<Long> machines, String routineName, UserBasic userBasicInfo, Long idx){
        Optional<Routine> routine = routineRepositoryImpl.findById(idx);
        List<RoutineList> routineLists = new ArrayList<>();
        for (Long machine : machines) {
                Machine machineIdx = machineRepository.findById(machine);
                RoutineList routineList = RoutineList.createRoutineList(routine.get(), machineIdx);
                routineLists.add(routineList);
        }
        routineListRepositoryImpl.saveAll(routineLists);
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
    public List<RoutineMachineDto> findByIdAndRoutineName(String userId, String RoutineName){
        Optional<Routine> routine = routineRepository.findByIdAndRoutineName(userId, RoutineName);
        if(routine.isEmpty()){
            return null;
        }
        List<RoutineList> routineLists = routineRepository.findByIdAndRoutineList(routine.get().getIdx());
        List<RoutineMachineDto> MachineLists = new ArrayList<>();
        for(RoutineList routineList : routineLists){
            MachineLists.add(new RoutineMachineDto(routineList.getIdx(),routineList.getMachine().getIdx(),routineList.getMachine().getKrMachineName(),
                    routineList.getMachine().getTargetArea(),routineList.getMachine().getImageUrl1()));
        }
        return MachineLists;
    }

    /** 루틴 하나 수정 UPDATE */
    public boolean modifyRoutine(String userId, RoutineModifyDto routineModifyDto){
        Optional<Routine> routineExist = routineRepository.findByIdAndRoutineName(userId, routineModifyDto.getRoutineName());
        //존재하지 않는 루틴일 경우 수정 불가능
        if (routineExist.isEmpty()) {
            return false;
        }
        Optional<Routine> routine = routineRepositoryImpl.findById(routineExist.get().getIdx());
        System.out.println("routineidx:"+routine.get().getIdx());
        List<RoutineList> routineExistList = routineRepository.findByRoutineListById(routineExist.get().getIdx());

        HashSet<Long> routineDtoSet = new HashSet<>(); /** 루틴 요청 저장할 기구 리스트*/
        HashSet<Long> routineExistSet = new HashSet<>(); /** 요청할 기구 리스트 */
        HashSet<Long> routineTempSet = new HashSet<>(); /** 차집합을 위한 임시 해시셋 */

        /** HashSet 설정 */
        for(int i=0;i<routineModifyDto.getMachines().size();i++){
            routineDtoSet.add(routineModifyDto.getMachines().get(i));
        }
        for(int i=0;i<routineExistList.size();i++){
            routineExistSet.add(routineExistList.get(i).getMachine().getIdx());
        }
        routineTempSet.addAll(routineExistSet); // A, A, B   기존, 기존복사, 요청
        routineExistSet.removeAll(routineDtoSet); // A-B, A, B 기존-요청, 기존복사, 요청
        routineDtoSet.removeAll(routineTempSet); //A-B,A ,B-A 기존-요청, 기존복사, 요청-기존복사

        UserBasic userBasic = userBasicRepository.findById(userId); //예시 userid

        /** UPDATE 중 SAVE */
        updateRoutineList(routine.get(),routineDtoSet);

        /** UPDATE 중 DELETE */
        deleteRoutineList(routine.get(),routineExistSet);

        /** UPDATE 후 루틴에 대한 모든 이름 변경 */
        changeRoutineNameList(routineModifyDto,userBasic);

        return true;
    }

    /**
     * 루틴수정Dto를 받아 기존 이름을 새로운 이름으로 바꾸는 함수
     * @param routineModifyDto
     * @param userBasic
     */
    public void changeRoutineNameList(RoutineModifyDto routineModifyDto,UserBasic userBasic) {
        String routineName = routineModifyDto.getRoutineName();
        String newRoutineName = routineModifyDto.getNewRoutineName();
        routineRepository.routineNameUpdate(routineName,newRoutineName,userBasic.getUserId());
    }

    /** 루틴내 기구만 업데이트 */
    public boolean updateRoutineList(Routine routine,HashSet<Long> routineDtoSet) {
        List<RoutineList> routineList = new ArrayList<>();
        for (Long machine : routineDtoSet) {
            Machine machineInfo = machineRepository.findById(machine);
            RoutineList routineLists = RoutineList.createRoutineList(routine,machineInfo);
            routineList.add(routineLists);
        }
        routineListRepositoryImpl.saveAll(routineList);
        return true;
    }

    /** 루틴내 기구만 삭제 */
    public boolean deleteRoutineList(Routine routine, HashSet<Long> routineExistSet) {
        for (Long machine : routineExistSet) {
            RoutineList routineList = routineRepository.findByMachineIdAndRoutineId(machine,routine.getIdx());
            routineRepository.deleteRoutineList(routineList.getIdx());
        }
        return true;
    }

    /** 루틴 하나 삭제 */
    public boolean deleteRoutine(String userId, String routineName){
        Optional<Routine> routine = routineRepository.findByIdAndRoutineName(userId,routineName);

        List<RoutineList> routineLists = routineRepository.findByIdAndRoutineList(routine.get().getIdx());
        for(RoutineList routineList : routineLists) {
            Long idx = routineList.getIdx();
            routineRepository.deleteRoutineList(idx);
        }
        routineRepository.deleteRoutine(routine.get().getIdx());
        return true;
    }

}


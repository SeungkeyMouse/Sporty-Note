package com.sportynote.server.service;

import com.sportynote.server.domain.*;
import com.sportynote.server.dto.Routinedto;
import com.sportynote.server.repository.MachineRepository;
import com.sportynote.server.repository.RoutineRepository;
import com.sportynote.server.repository.UserBasicRepository;
import com.sportynote.server.repository.UserFavoriteRepository;
import com.sportynote.server.repository.query.MachineDto;
import com.sportynote.server.repository.query.RoutineDto;
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
        try {
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
        } catch (NoResultException e) {
            return false;
        }
    }


    public Set<String> myRoutine(String userid) {
        List<Routine> RoutineLists = routineRepository.findByUserId(userid);
        Set<String> RoutineNames = new TreeSet<String>();
        for(Routine routine : RoutineLists){
            RoutineNames.add(routine.getRoutineName());
        }
        return RoutineNames;
    }
}

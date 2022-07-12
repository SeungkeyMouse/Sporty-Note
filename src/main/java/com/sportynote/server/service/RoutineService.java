package com.sportynote.server.service;

import com.sportynote.server.domain.*;
import com.sportynote.server.repository.RoutineRepository;
import com.sportynote.server.repository.UserFavoriteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RoutineService {
    private final RoutineRepository routineRepository;

    /**
     루틴 하나 추가 CREATE
     */
    public Boolean addRoutine(String userId, Routine routine) {
        //루틴 저장
        try {
            routineRepository.save(routine);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

}

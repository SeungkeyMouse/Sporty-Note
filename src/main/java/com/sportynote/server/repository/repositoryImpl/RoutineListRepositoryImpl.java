package com.sportynote.server.repository.repositoryImpl;

import com.sportynote.server.domain.RoutineList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoutineListRepositoryImpl extends JpaRepository<RoutineList,Long> {

}

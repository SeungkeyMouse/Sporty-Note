package com.sportynote.server.repository.query;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RoutineModifyDto {
    private String routineName;
    private String newRoutineName;
    private List<Long> Machines;
    public RoutineModifyDto(){

    }
}

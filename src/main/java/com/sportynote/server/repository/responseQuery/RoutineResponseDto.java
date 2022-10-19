package com.sportynote.server.repository.responseQuery;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;


@Data
@AllArgsConstructor
public class RoutineResponseDto {
    private String routineName;
    private List<Long> Machines;
    private Integer routineNameIdx;

    public RoutineResponseDto(){

    }
}


package com.sportynote.server.repository.query;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class RoutineDto {
    private String routineName;
    private List<Long> Machines;

    public RoutineDto(){

    }

}
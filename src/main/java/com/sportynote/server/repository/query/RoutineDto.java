package com.sportynote.server.repository.query;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class RoutineDto {
    private String userid;
    private String RoutineName;
    private List<Integer> Machines = new ArrayList<>();

}
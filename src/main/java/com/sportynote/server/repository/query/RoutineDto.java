package com.sportynote.server.repository.query;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class RoutineDto {
    private String routine_Idx;
    private String userid;
    private String routineName;
    private List<Integer> Machines = new ArrayList<>();

    /**
     * PK 값을 넘겨준 상태서 머신을 수정하려고 할 때
     */
    @Getter
    @Setter
    @AllArgsConstructor
    public static class MachineRoutineDto {
        private List<Integer> Primary_key = new ArrayList<>();
        private String routine_Idx;
        private String userid;
        private String routineName;
        private List<Integer> Machines = new ArrayList<>();
    }
}
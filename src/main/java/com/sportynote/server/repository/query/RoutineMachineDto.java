package com.sportynote.server.repository.query;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoutineMachineDto {
    //내 기구 정보 내 루틴 idx
    private Long routineIdx;
    private Long machineIdx;
    private String machineName;
    private String targetArea;
    private String url;
}
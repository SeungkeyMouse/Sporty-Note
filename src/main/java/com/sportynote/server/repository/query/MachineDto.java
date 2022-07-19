package com.sportynote.server.repository.query;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MachineDto {
    //기구 정보
    private String machineName;
    private String targetArea;
    private String Url;
}

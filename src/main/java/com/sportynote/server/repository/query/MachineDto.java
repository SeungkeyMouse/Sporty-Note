package com.sportynote.server.repository.query;

import com.sportynote.server.domain.UserFavorite;
import com.sun.istack.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
public class MachineDto {

    //내 기구 정보
    private Long machineIdx;
    private String krMachineName;
    private String engMachineName;
    private String targetArea;
    private String url;

    private Long userFavoriteIdx;

    public MachineDto(Long machineIdx, String krMachineName, String engMachineName, String targetArea, String url){
        this.machineIdx = machineIdx;
        this.krMachineName = krMachineName;
        this.engMachineName = engMachineName;
        this.targetArea = targetArea;
        this.url = url;
    }
}
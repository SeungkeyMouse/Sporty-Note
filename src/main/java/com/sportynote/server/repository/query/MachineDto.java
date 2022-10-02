package com.sportynote.server.repository.query;

import com.sportynote.server.domain.UserFavorite;
import com.sun.istack.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MachineDto {

    //내 기구 정보
    private Long machineIdx;
    private String krMachineName;
    private String engMachineName;
    private String targetArea;
    private String imageUrl1;
    private String imageUrl2;
    private String videoUrl1;

    private Long userFavoriteIdx;

    public MachineDto(Long machineIdx, String krMachineName, String engMachineName, String targetArea, String imageUrl1
    , String imageUrl2, String videoUrl1){
        this.machineIdx = machineIdx;
        this.krMachineName = krMachineName;
        this.engMachineName = engMachineName;
        this.targetArea = targetArea;
        this.imageUrl1 = imageUrl1;
        this.imageUrl2 = imageUrl2;
        this.videoUrl1 = videoUrl1;
    }
}
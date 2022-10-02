package com.sportynote.server.repository.query;

import com.sportynote.server.type.NodeType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class NoteDto {
    //하나의 노트페이지를 불러오는 정보
    private Long noteIdx;

    private MachineDto machineDto;

    private Map<NodeType, List<NodeDto>> nodeDtos;
}

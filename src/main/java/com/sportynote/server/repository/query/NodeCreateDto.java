package com.sportynote.server.repository.query;

import com.sportynote.server.Enum.NodeType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Data
public class NodeCreateDto {
    private Long noteIdx;
    private String userIdx;
    private Long machineIdx;

    //노드에 대한정보
    //종류

    private NodeType type;
    private String color;

    //내용
    private String text;

    private Float x_location;
    private Float y_location;


    private String pictureUrl;
}

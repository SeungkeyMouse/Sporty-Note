package com.sportynote.server.repository.query;

import com.sportynote.server.type.NodeType;
import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
@Data
public class NodeCreateDto {
    private Long noteIdx;
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

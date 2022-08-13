package com.sportynote.server.repository.query;

import com.sportynote.server.Enum.NodeType;
import lombok.Data;

@Data
public class NodeUpdateDto {
    private Long nodeIdx;

    private NodeType type;
    private String color;

    private String text;

    private String pictureUrl;
}

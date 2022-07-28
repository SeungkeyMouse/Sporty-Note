package com.sportynote.server.repository.query;

import com.sportynote.server.Enum.NodeType;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Data
public class NodeLocationDto {
    private Integer machineId;

    private NodeType nodeType;

    private Float x_location;

    private Float y_location;
}

package com.sportynote.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sportynote.server.Enum.NodeType;
import com.sportynote.server.repository.query.NodeCreateDto;
import com.sportynote.server.repository.query.NodeLocationDto;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class NodeLocationSet {
    @Id
    @GeneratedValue
    private Integer idx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "machine_idx")
    @NotNull
    private Machine machine;

    @Enumerated(EnumType.STRING)
    private NodeType nodeType;

    @NotNull
    private Float x_location;

    @NotNull
    private Float y_location;

    //==생성 메서드==//
    public static NodeLocationSet createNodeLocationSet(Machine machine, NodeLocationDto nodeLocationDto) {
        NodeLocationSet ns = new NodeLocationSet();
        ns.setX_location(nodeLocationDto.getX_location());
        ns.setY_location(nodeLocationDto.getY_location());
        ns.setNodeType(NodeType.findNodeType(nodeLocationDto.getNodeType().getEngName()));
        ns.setMachine(machine);

        return ns;
    }
}

package com.sportynote.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sportynote.server.Enum.NodeType;
import com.sportynote.server.domain.base.BaseEntity;
import com.sportynote.server.repository.query.NodeCreateDto;
import com.sportynote.server.repository.query.NodeLocationDto;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
@SQLDelete(sql = "UPDATE node_location_set SET deleted = true WHERE user_favorite_idx = ?")
@Where(clause = "deleted=false")
public class NodeLocationSet extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "node_location_set_idx")
    private Long idx;

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

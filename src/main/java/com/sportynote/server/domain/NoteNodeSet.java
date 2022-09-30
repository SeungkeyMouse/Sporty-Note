package com.sportynote.server.domain;

import com.sportynote.server.type.NodeType;
import com.sportynote.server.domain.base.BaseEntity;
import com.sportynote.server.repository.query.NodeSetCreateDto;
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
@SQLDelete(sql = "UPDATE note_node_set SET deleted = true WHERE note_node_set_idx = ?")
@Where(clause = "deleted=false")
public class NoteNodeSet extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "note_node_set_idx")
    private Long idx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "machine_idx")
    @NotNull
    private Machine machine;

    @Enumerated(EnumType.STRING)
    private NodeType type;//부위 ex) 1-팔꿈치 / 2-가슴 / 3-다리 / 4-등(허리)

    private String color;


    private String text;

    private Float x_location;
    private Float y_location;

    private String pictureUrl;

    //==생성 메서드==//
    public static NoteNodeSet createNode(Machine machine, NodeSetCreateDto nodeCreateDto) {
        NoteNodeSet nodeSet = new NoteNodeSet();
        nodeSet.setMachine(machine);
        nodeSet.setType(nodeCreateDto.getType());
        nodeSet.setColor(nodeCreateDto.getColor());
        nodeSet.setText(nodeCreateDto.getText());
        nodeSet.setX_location(nodeCreateDto.getX_location());
        nodeSet.setY_location(nodeCreateDto.getY_location());
        nodeSet.setPictureUrl(nodeCreateDto.getPictureUrl());

        return nodeSet;
    }
}
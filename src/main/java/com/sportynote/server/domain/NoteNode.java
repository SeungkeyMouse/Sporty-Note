package com.sportynote.server.domain;

import com.sportynote.server.type.NodeType;
import com.sportynote.server.domain.base.BaseEntity;
import com.sportynote.server.repository.query.NodeCreateDto;
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
@SQLDelete(sql = "UPDATE note_node SET deleted = true WHERE note_node_idx = ?")
@Where(clause = "deleted=false")
public class NoteNode extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "note_node_idx")
    private Long idx;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private Note note;

    @Enumerated(EnumType.STRING)
    private NodeType type;//부위 ex) 1-팔꿈치 / 2-가슴 / 3-다리 / 4-등(허리)

    private String color;


    private String text;
    private Float x_location;
    private Float y_location;

    private String pictureUrl;

    //==생성 메서드==//
    public static NoteNode createNode(Note note, NodeCreateDto nodeCreateDto) {
        NoteNode node = new NoteNode();
        node.setNote(note);

        node.setType(nodeCreateDto.getType());
        node.setColor(nodeCreateDto.getColor());
        node.setText(nodeCreateDto.getText());
        node.setX_location(nodeCreateDto.getX_location());
        node.setY_location(nodeCreateDto.getY_location());
        node.setPictureUrl(nodeCreateDto.getPictureUrl());


        return node;
    }
}

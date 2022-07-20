package com.sportynote.server.domain;

import com.sportynote.server.repository.query.NodeDto;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.w3c.dom.Node;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class NoteNode {
    @Id
    @GeneratedValue
    private Integer idx;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private Note note;

    private Integer type;//부위 ex) 1-팔꿈치 / 2-가슴 / 3-다리 / 4-등(허리)
    private String color;


    private String text;
    private Float x_location;
    private Float y_location;

    private String pictureUrl;

    //==생성 메서드==//
    public static NoteNode createNode(Note note, NodeDto nodeDto) {
        NoteNode node = new NoteNode();
        node.setNote(note);
        node.setType(nodeDto.getType());
        node.setColor(nodeDto.getColor());
        node.setText(nodeDto.getText());
        node.setX_location(nodeDto.getX_location());
        node.setY_location(nodeDto.getY_location());
        node.setPictureUrl(nodeDto.getPictureUrl());

        return node;
    }
}

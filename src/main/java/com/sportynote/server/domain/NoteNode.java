package com.sportynote.server.domain;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    private String color;
    private String text;
    private Integer type;//부위 ex) 1-팔꿈치 / 2-가슴 / 3-다리 / 4-등(허리)
    private Float x_location;
    private Float y_location;

}

package com.sportynote.server.domain;

import com.sportynote.server.domain.base.BaseEntity;
import com.sportynote.server.repository.query.NodeDto;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Note extends BaseEntity {
    @Id
    @GeneratedValue
    private Integer idx;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private UserBasic userBasic;

    @OneToOne(fetch = FetchType.LAZY)
    @NotNull
    private Machine machine;

    @OneToMany(mappedBy = "note", cascade= CascadeType.ALL)
    private List<NoteNode> noteNode = new ArrayList<>();


    //==생성 메서드==//
    public static Note createNote(UserBasic userBasic, Machine orgMachine) {
        Note note = new Note();
        note.setUserBasic(userBasic);
        note.setMachine(orgMachine);

        return note;
    }
}

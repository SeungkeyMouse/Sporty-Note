package com.sportynote.server.domain;

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
public class Note {
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
    public static Note createNote(UserBasic userBasic, Machine orgMachine,NoteNode noteNode) {
        UserFavorite userFavorite = new UserFavorite();
        userFavorite.setUserBasic(userBasic);
        userFavorite.setMachine(orgMachine);

        Note note = new Note();
        note.setUserBasic(userBasic);
        note.setMachine(orgMachine);
        note.getNoteNode().add(noteNode);

        return note;
    }
}

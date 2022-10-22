package com.sportynote.server.domain;

import com.sportynote.server.domain.base.BaseEntity;
import com.sportynote.server.repository.query.NodeDto;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@SQLDelete(sql = "UPDATE note SET deleted = true WHERE note_idx = ?")
@Where(clause = "deleted=false")
public class Note extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "note_idx")
    private Long idx;

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

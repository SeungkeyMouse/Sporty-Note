package com.sportynote.server.domain;

import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name ="routine_table")
public class Routine {
    @Id
    @GeneratedValue
    private Integer idx;

    @NotNull
    private String routineName;

//    @NotNull
//    @JoinColumn(name= "Idx")
//    @ManyToOne(fetch = FetchType.LAZY)
//    private UserBasic userid;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Machine machine;

}
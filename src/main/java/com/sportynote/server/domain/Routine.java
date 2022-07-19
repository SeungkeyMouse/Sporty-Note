package com.sportynote.server.domain;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name ="routine_table")
public class Routine {
    @Id
    @GeneratedValue
    @Column(name="Routine_Idx")
    private Integer idx;

    @NotNull
    private String routineName;

    @NotNull
    @JoinColumn(name= "user_id",referencedColumnName = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private UserBasic userBasic;

    @NotNull
    @JoinColumn(name="machine_id")
    @ManyToOne(cascade = CascadeType.ALL)
    private Machine machine;


    @Builder
    public Routine(String routineName, UserBasic userBasic, Machine machine){
        this.routineName=routineName;
        this.userBasic=userBasic;
        this.machine=machine;
    }

    //==Routine 생성 메서드==//
    public static Routine createRoutine(String routineName,UserBasic userBasic, Machine machine) {
        return Routine.builder()
                .routineName(routineName)
                .userBasic(userBasic)
                .machine(machine)
                .build();
    }


}
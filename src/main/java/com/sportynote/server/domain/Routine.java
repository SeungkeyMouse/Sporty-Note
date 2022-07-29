package com.sportynote.server.domain;

import com.sportynote.server.domain.base.BaseEntity;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.SQLDelete;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@Table(name ="routine_table")
@SQLDelete(sql = "UPDATE routine_table SET deleted = true WHERE idx = ?")
public class Routine extends BaseEntity {
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
    @ManyToOne
    private Machine machine;

    public Routine(String routineName, UserBasic userBasic, Machine machine){
        this.routineName=routineName;
        this.userBasic=userBasic;
        this.machine=machine;
    }

    @Builder //수정 빌더
    public Routine(String routineName, UserBasic userBasic, Machine machine,Integer idx){
        this.idx=idx;
        this.routineName=routineName;
        this.userBasic=userBasic;
        this.machine=machine;
    }

    //==Routine 생성 메서드==//
    public static Routine createRoutine(String routineName, UserBasic userBasic, Machine machine) {
        return Routine.builder()
                .routineName(routineName)
                .userBasic(userBasic)
                .machine(machine)
                .build();
    }

    //==Routine 수정 메서드==//
    public static Routine updateRoutine(Integer idx, String routineName, UserBasic userBasic, Machine machine) {
        return Routine.builder()
                .idx(idx)
                .routineName(routineName)
                .userBasic(userBasic)
                .machine(machine)
                .build();
    }

}
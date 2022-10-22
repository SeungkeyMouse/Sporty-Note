package com.sportynote.server.domain;

import com.sportynote.server.domain.base.BaseEntity;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@Table(name ="routine_list_table")
@SQLDelete(sql = "UPDATE routine_list_table SET deleted = true WHERE routine_list_idx = ?")
@Where(clause = "deleted=false")
public class RoutineList extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="routine_list_idx")
    private Long idx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "routine_routine_idx",referencedColumnName = "routine_idx")
    @NotNull
    private Routine routine;

    @NotNull
    @JoinColumn(name="machine_idx")
    @ManyToOne
    private Machine machine;

    @Builder
    public RoutineList(Routine routine, Machine machine) {
        this.routine = routine;
        this.machine = machine;
    }

    //==Routine 생성 메서드==//
    public static RoutineList createRoutineList(Routine routine, Machine machine) {
        return RoutineList.builder()
                .routine(routine)
                .machine(machine)
                .build();
    }


}
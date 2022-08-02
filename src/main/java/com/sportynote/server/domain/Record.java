package com.sportynote.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sportynote.server.domain.base.BaseEntity;
import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;

import javax.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name ="record_table")
public class Record extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_idx")
    private Long idx;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    @NotNull
    private UserBasic userBasic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="machine_Idx")
    @NotNull
    private Machine machine;

    @NotNull
    private Integer sett;

    @NotNull
    private Integer kg;

    @NotNull
    private Integer count;

    private boolean complete;

    @Builder
    public Record(UserBasic userBasic, Machine machine, Integer sett, Integer kg, Integer count, boolean complete) {
        this.userBasic = userBasic;
        this.machine = machine;
        this.sett = sett;
        this.kg = kg;
        this.count=count;
        this.complete = complete;
    }

    //==Routine 생성 메서드==//
    public static Record createRecord(UserBasic userBasic, Machine machine, Integer sett, Integer kg, Integer count, boolean complete) {
        return Record.builder()
                .userBasic(userBasic)
                .machine(machine)
                .sett(sett)
                .kg(kg)
                .count(count)
                .complete(complete)
                .build();
    }

}

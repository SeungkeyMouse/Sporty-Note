package com.sportynote.server.domain;

import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Routine {
    @Id
    @GeneratedValue
    @Column
    private Integer idx;

    @NotNull
    private String routineName;



//    @NotNull
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "gym_idx")
//    private String user_id;

    @NotNull
    private String machineId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gym_idx")
    private Gym gym;
}
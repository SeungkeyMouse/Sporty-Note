package com.sportynote.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Machine {
    @Id
    @GeneratedValue
    @Column(name = "machine_Idx")
    private Integer idx;

    @NotNull
    private String machineName;

    @NotNull
    private String targetArea;

    @NotNull
    private String Url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gym_idx")
    private Gym gym;

    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    private UserFavorite userFavorite;


//    @JsonIgnore
//    @OneToMany(fetch = FetchType.LAZY)
//    private List<Routine> routines = new ArrayList<>();

    //==생성 메서드==//
    public static Machine createFavoriteMachine(Machine orgMachine) {
        Machine favoriteMachine = new Machine();
        favoriteMachine.setMachineName(orgMachine.getMachineName());
        favoriteMachine.setTargetArea(orgMachine.getTargetArea());
        favoriteMachine.setUrl(orgMachine.getUrl());

        if(orgMachine.getGym() != null){//나중에 Optional로 바꿀 필요있음
            favoriteMachine.setGym(orgMachine.getGym());
        }
        return favoriteMachine;
    }
}
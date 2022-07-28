package com.sportynote.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Machine {
    @Id
    @GeneratedValue
    @Column(name = "machine_idx")
    private Integer idx;

    @NotNull
    private String machineName;

    @Nullable
    private String targetArea;

    @Nullable
    private String Url;

//    @JsonIgnore
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "gym_idx")
//    private Gym gym;

    @JsonIgnore
    @OneToMany(mappedBy = "machine", cascade= CascadeType.ALL)
    private List<UserFavorite> userFavorites = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "machine", cascade= CascadeType.ALL)
    private List<Routine> routines = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "machine", cascade= CascadeType.ALL)
    private List<NodeLocationSet> nodeLocationSets =  new ArrayList<>();
    @Builder
    public Machine(Integer idx, String machineName, String targetArea, String Url){
        this.idx=idx;
        this.machineName=machineName;
        this.targetArea=targetArea;
        this.Url=Url;
    }

    public static Machine createMachine(String machineName,String targetArea,String Url) {
        return Machine.builder()
                .machineName(machineName)
                .targetArea(targetArea)
                .Url(Url)
                .build();
    }
}
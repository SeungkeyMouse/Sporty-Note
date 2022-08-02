package com.sportynote.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sportynote.server.domain.base.BaseEntity;
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
public class Machine extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "machine_idx")
    private Long idx;

    @NotNull
    private String krMachineName;

    @NotNull
    private String engMachineName;

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

    @JsonIgnore
    @OneToMany(mappedBy = "machine", cascade= CascadeType.ALL)
    private List<Record> recordLists =  new ArrayList<>();

    @Builder
    public Machine(Long idx, String krMachineName, String engMachineName, String targetArea, String url){
        this.idx=idx;
        this.krMachineName=krMachineName;
        this.engMachineName = engMachineName;
        this.targetArea=targetArea;
        this.Url=url;
    }

    public static Machine createMachine(String krMachineName,String engMachineName, String targetArea,String url) {
        return Machine.builder()
                .krMachineName(krMachineName)
                .engMachineName(engMachineName)
                .targetArea(targetArea)
                .url(url)
                .build();
    }
}
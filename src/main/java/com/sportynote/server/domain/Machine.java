package com.sportynote.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sportynote.server.domain.base.BaseEntity;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.lang.Nullable;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@SQLDelete(sql = "UPDATE machine SET deleted = true WHERE machine_idx = ?")
@Where(clause = "deleted=false")
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
    private String imageUrl1;

    @Nullable
    private String imageUrl2;

    @Nullable
    private String videoUrl1;

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

    @JsonIgnore
    @OneToMany(mappedBy = "machine", cascade= CascadeType.ALL)
    private List<NoteNodeSet> nodeSets =  new ArrayList<>();

    @Builder
    public Machine(Long idx, String krMachineName, String engMachineName, String targetArea, String imageUrl1, String imageUrl2, String videoUrl1){
        this.idx=idx;
        this.krMachineName=krMachineName;
        this.engMachineName = engMachineName;
        this.targetArea=targetArea;
        this.imageUrl1=imageUrl1;
        this.imageUrl2=imageUrl2;
        this.videoUrl1=videoUrl1;

    }

    public static Machine createMachine(String krMachineName,String engMachineName, String targetArea,String imageUrl1, String imageUrl2, String videoUrl1) {
        return Machine.builder()
                .krMachineName(krMachineName)
                .engMachineName(engMachineName)
                .targetArea(targetArea)
                .imageUrl1(imageUrl1)
                .imageUrl2(imageUrl2)
                .videoUrl1(videoUrl1)
                .build();
    }
}
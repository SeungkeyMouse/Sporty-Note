package com.sportynote.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

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
    @Column(name = "machine_id")
    private Integer idx;

    @NotNull
    private String machineName;

    @NotNull
    private String targetArea;

    @NotNull
    private String Url;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gym_idx")
    private Gym gym;

    @JsonIgnore
    @OneToMany(mappedBy = "machine", cascade= CascadeType.ALL)
    private List<UserFavorite> userFavorites = new ArrayList<>();


    @JsonIgnore
    @OneToMany(mappedBy = "machine", cascade= CascadeType.ALL)
    private List<Routine> routines = new ArrayList<>();

}
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
public class Machine {
    @Id
    @GeneratedValue
    @Column
    private Integer idx;

    @NotNull
    private String machineName;

    @NotNull
    private String stimulationPart;

    @NotNull
    private String Url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gym_idx")
    private Gym gym;
}

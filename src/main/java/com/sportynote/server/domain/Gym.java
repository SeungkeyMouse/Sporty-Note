package com.sportynote.server.domain;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@Entity
@RequiredArgsConstructor
@NoArgsConstructor
public class Gym {
    @Id
    @GeneratedValue
    @Column(name= "gym_idx")
    private Integer idx;

    @NotNull
    private String name;

    @NotNull
    private String latitude;

    @NotNull
    private String longitude;

    private String si;

    private String gu;

    private String dong;
}

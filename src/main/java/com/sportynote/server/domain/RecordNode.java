package com.sportynote.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name ="routine_node_table")
public class RecordNode {
    @Id
    @GeneratedValue
    @Column(name = "machine_idx")
    private Integer idx;

    @NotNull
    private String userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="machine_id")
    @NotNull
    private Routine machineId;

    @NotNull
    private Integer noteId;

    @NotNull
    private Integer complete;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gym_idx")
    private Gym gym;

    @JsonIgnore
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "userFavorite_idx")
    private UserFavorite userFavorite;
}
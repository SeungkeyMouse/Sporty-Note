package com.sportynote.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sportynote.server.domain.base.BaseEntity;
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
@Table(name ="record_node_table")
public class RecordNode extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_node_idx")
    private Long idx;

    @NotNull
    private String userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="machine_id")
    @NotNull
    private Machine machine;

    @NotNull
    private Long noteId;

    @NotNull
    private Integer complete;


//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "gym_idx")
//    private Gym gym;
//
//    @JsonIgnore
//    @ManyToOne(fetch = LAZY)
//    @JoinColumn(name = "userFavorite_idx")
//    private UserFavorite userFavorite;
}
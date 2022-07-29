package com.sportynote.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sportynote.server.domain.base.BaseEntity;
import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name ="record_table")
public class Record extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "record_idx")
    private Long idx;

    @NotNull
    private String userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="machine_Idx")
    @NotNull
    private Machine machines;

    @NotNull
    private Long noteId;

    @NotNull
    private LocalDateTime recordedDate;

    @NotNull
    private Integer set;

    @NotNull
    private Integer kg;

    @NotNull
    private Integer complete;

}

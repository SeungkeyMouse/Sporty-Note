package com.sportynote.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name ="routine_table")
public class Record {
    @Id
    @GeneratedValue
    private Integer idx;

    @NotNull
    private String userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="machine_Idx")
    @NotNull
    private Machine machines;

    @NotNull
    private Integer noteId;

    @NotNull
    private LocalDateTime recordedDate;

    @NotNull
    private Integer set;

    @NotNull
    private Integer kg;

    @NotNull
    private Integer complete;

}

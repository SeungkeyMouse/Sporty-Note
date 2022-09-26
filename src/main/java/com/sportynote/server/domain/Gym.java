package com.sportynote.server.domain;

import com.sportynote.server.domain.base.BaseEntity;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Getter
@Setter
@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE Gym SET deleted = true WHERE user_favorite_idx = ?")
@Where(clause = "deleted=false")
public class Gym extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "gym_idx")
    private Long idx;

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

package com.sportynote.server.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Entity
public class UserBasic {
    @Id
    @GeneratedValue
    @Column
    private Integer idx;


    @NotNull
    @Column(name = "user_id")
    private String userId;

    @JsonIgnore
    @OneToMany(mappedBy = "userBasic")
    private List<UserFavorite> userFavorites = new ArrayList<>();
    //생략
}
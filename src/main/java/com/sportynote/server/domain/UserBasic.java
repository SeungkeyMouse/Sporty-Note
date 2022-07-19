package com.sportynote.server.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.sportynote.server.Enum.SocialType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class UserBasic implements Serializable {
    @Id
    @GeneratedValue
    @Column(name="idx")
    private Integer idx;

    @NotNull
    @Column(name = "user_id")
    private String userId;

    @JsonIgnore
    @OneToMany(mappedBy = "userBasic")
    private List<UserFavorite> userFavorites = new ArrayList<>();

    private String email;

    private String name;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;

}
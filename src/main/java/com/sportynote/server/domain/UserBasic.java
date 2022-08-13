package com.sportynote.server.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sportynote.server.domain.base.BaseEntity;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.sportynote.server.Enum.SocialType;
<<<<<<< HEAD
import org.hibernate.annotations.SQLDelete;
=======
>>>>>>> e356767f084accefdb147c73c6a441ef3fdb504d

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
<<<<<<< HEAD
@SQLDelete(sql = "UPDATE user_Basic SET deleted = true WHERE user_basic_idx = ?")
=======
>>>>>>> e356767f084accefdb147c73c6a441ef3fdb504d
public class UserBasic extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_basic_idx")
    private Long idx;

    @NotNull
    @Column(name = "user_id")
    private String userId;

    @Column(name = "oauth_id")
    private String oauthId;

    @JsonIgnore
    @OneToMany(mappedBy = "userBasic")
    private List<UserFavorite> userFavorites = new ArrayList<>();

    private String email;

    private String name;

    @Enumerated(EnumType.STRING)
    private SocialType socialType;

    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;

    @Builder
    public UserBasic(String userId, String oauthId, String email, String name, SocialType socialType) {
        this.userId = userId;
        this.oauthId = oauthId;
        this.email = email;
        this.name = name;
        this.socialType = socialType;
    }

    //==UserBasic 생성 메서드==//
    public static UserBasic createdUserBasic(String email, String oauthId, String name, String userId, SocialType socialType) {
        return UserBasic.builder()
                .email(email)
                .oauthId(oauthId)
                .name(name)
                .userId(userId)
                .socialType(socialType)
                .build();
    }

    protected UserBasic(){
    }

    private String roles="";
    private String permissions="";
    public List<String> getRoleList(){
        if(this.roles.length()>0){
            return Arrays.asList(this.roles.split(","));
        }

        return new ArrayList<>();
    }

    public List<String> getPermissionList(){
        if(this.permissions.length()>0){
            return Arrays.asList(this.permissions.split(","));
        }

        return new ArrayList<>();
    }






}
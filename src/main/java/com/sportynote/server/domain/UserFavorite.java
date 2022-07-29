package com.sportynote.server.domain;

import com.sportynote.server.domain.base.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.catalina.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor

public class UserFavorite extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "user_favorite_id")
    private Integer idx;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserBasic userBasic;


    @ManyToOne(fetch = FetchType.LAZY)//양방향을 지향해야함
    private Machine machine;


    //==연관관계 메서드==//
    public void setUserBasic(UserBasic userBasic) {
        this.userBasic = userBasic;
        userBasic.getUserFavorites().add(this);
    }


    //==생성 메서드==//
    public static UserFavorite createFavorite(UserBasic userBasic, Machine orgMachine) {
        UserFavorite userFavorite = new UserFavorite();
        userFavorite.setUserBasic(userBasic);
        userFavorite.setMachine(orgMachine);

        return userFavorite;
    }
}

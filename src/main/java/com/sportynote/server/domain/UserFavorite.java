package com.sportynote.server.domain;

import com.sportynote.server.domain.base.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.catalina.User;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@SQLDelete(sql = "UPDATE user_favorite SET deleted = true WHERE user_favorite_idx = ?")
@Where(clause = "deleted=false")
public class UserFavorite extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_favorite_idx")
    private Long idx;

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

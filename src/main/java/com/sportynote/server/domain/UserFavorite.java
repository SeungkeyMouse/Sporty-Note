package com.sportynote.server.domain;

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
public class UserFavorite {
    @Id
    @GeneratedValue
    @Column(name = "userFavorite_idx")
    private Integer idx;

    @ManyToOne(fetch = FetchType.LAZY)
    private UserBasic userBasic;


    @OneToMany(mappedBy = "userFavorite", cascade = CascadeType.ALL)//양방향을 지향해야함
    private List<Machine> userFavoriteMachines = new ArrayList<>();


    //==연관관계 메서드==//
    public void setUserBasic(UserBasic userBasic) {
        this.userBasic = userBasic;
        userBasic.getUserFavorites().add(this);
    }

    public void addFavoriteMachine(Machine machine) {
        userFavoriteMachines.add(machine);
        machine.setUserFavorite(this);
    }

    //==생성 메서드==//
    public static UserFavorite createFavorite(UserBasic userBasic, Machine orgMachine) {
        UserFavorite userFavorite = new UserFavorite();
        userFavorite.setUserBasic(userBasic);
        userFavorite.addFavoriteMachine(orgMachine);

        return userFavorite;
    }
}

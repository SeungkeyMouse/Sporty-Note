package com.sportynote.server.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Getter
@Setter
@Entity
public class UserBasic {
    @Id
    @GeneratedValue
    @Column
    private Integer idx;

    //생략
}
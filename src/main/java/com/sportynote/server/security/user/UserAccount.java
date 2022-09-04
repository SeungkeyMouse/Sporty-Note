package com.sportynote.server.security.user;

import com.sportynote.server.domain.UserBasic;
import com.sportynote.server.security.UserBasicPrincipal;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

@Getter
public class UserAccount extends User {
    private UserBasicPrincipal userBasicPrincipal;

    public UserAccount(UserBasicPrincipal userBasicPrincipal){
        super(userBasicPrincipal.getUsername(),userBasicPrincipal.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_USER")));
        this.userBasicPrincipal=userBasicPrincipal;
    }
}

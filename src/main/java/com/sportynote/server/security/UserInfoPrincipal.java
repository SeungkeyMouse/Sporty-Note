//package com.sportynote.server.security;
//
//import java.io.Serializable;
//import com.sch.whiteboard.entity.UserInfo;
//import com.sportynote.server.domain.UserBasic;
//import lombok.AllArgsConstructor;
//import org.springframework.security.core.userdetails.User;
//import lombok.Getter;
//
//@Getter
//public class UserInfoPrincipal extends User implements Serializable{
//
//    private final UserBasic userBasic;
//
//    // User 클래스를 상속받기 때문에 super를 통해 User의 생성자를 수행한 후
//    // 추가적인 정보가 담겨있는 UserInfo를 인자로 받아 데이터를 담는다.
//    public UserInfoPrincipal(UserBasic userBasic) {
//        super(userInfo.getUsername(), "", userInfo.getAuthorities());
//        this.userBasic = userBasic;
//    }
//
//    public UserInfoPrincipal(UserInfo userInfo) {
//        super(userInfo.getUsername(), "", userInfo.getAuthorities());
//        this.userInfo = userInfo;
//    }
//    public UserBasicPrincipal(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities, UserLoginDto user) {
//        super(principal, credentials, user.getAuthorities());
//        this.user = user;
//        //principal : userId
//        //credentials : password
//    }
//    @Override
//    public Object getDetails() {
//        return user;
//    }
//
//}
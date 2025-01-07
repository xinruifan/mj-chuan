package com.mj.mjchuan.infrastructure.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.util.Collections;


public class JwtAuthenticationToken extends AbstractAuthenticationToken {


    public final UserJwt userJwt;

    public JwtAuthenticationToken(UserJwt userJwt) {
        super(Collections.emptyList());  // 初始权限为空
        this.userJwt = userJwt;
        setAuthenticated(true);  // 表示已经认证过
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return userJwt;
    }

}

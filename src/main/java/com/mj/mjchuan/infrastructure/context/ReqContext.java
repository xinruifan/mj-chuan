package com.mj.mjchuan.infrastructure.context;

import com.mj.mjchuan.infrastructure.security.JwtAuthenticationToken;
import com.mj.mjchuan.infrastructure.security.UserJwt;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author xinruifan
 * @create 2025-01-08 16:01
 */
public class ReqContext {

    public static UserJwt get() {
        JwtAuthenticationToken authentication = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        return authentication.userJwt;
    }


    public static Long getUserId() {
        return ReqContext.get().getId();
    }
}

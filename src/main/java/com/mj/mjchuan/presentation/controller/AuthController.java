package com.mj.mjchuan.presentation.controller;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.mj.mjchuan.infrastructure.security.JwtAuthenticationToken;
import com.mj.mjchuan.infrastructure.security.JwtTokenUtil;
import com.mj.mjchuan.infrastructure.security.UserJwt;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {


    @GetMapping(value = "/login")
    public String login(String account) {
        // 这里可以做实际的身份验证（例如验证用户名和密码）
        // 如果验证通过，生成 JWT
        UserJwt userJwt = new UserJwt();
        userJwt.setAccount(account);
        return JwtTokenUtil.generateToken(userJwt);
    }



    @GetMapping(value ="/protected")
    public String protectedApi() {
        JwtAuthenticationToken authentication = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserJwt userJwt = authentication.userJwt;
        return "This is a protected API + " + JSONUtil.parse(userJwt);
    }
}

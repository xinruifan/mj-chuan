package com.mj.mjchuan.presentation.controller;

import cn.hutool.json.JSONUtil;
import com.mj.mjchuan.domain.user.service.UserService;
import com.mj.mjchuan.infrastructure.security.JwtAuthenticationToken;
import com.mj.mjchuan.infrastructure.security.JwtTokenUtil;
import com.mj.mjchuan.infrastructure.security.UserJwt;
import com.mj.mjchuan.presentation.req.UserLoginReq;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class AuthController {

    @Resource
    private UserService userService;

    @PostMapping(value = "/login")
    public String login(@RequestBody UserLoginReq userLoginReq) {
        return userService.login(userLoginReq);
    }



    @GetMapping(value ="/protected")
    public String protectedApi() {
        JwtAuthenticationToken authentication = (JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        UserJwt userJwt = authentication.userJwt;
        return "This is a protected API + " + JSONUtil.parse(userJwt);
    }
}

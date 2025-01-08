package com.mj.mjchuan.presentation.controller;

import com.mj.mjchuan.domain.user.service.UserService;
import com.mj.mjchuan.presentation.req.UserLoginReq;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class AuthController {

    @Resource
    private UserService userService;

    @PostMapping(value = "/login")
    public String login(@RequestBody UserLoginReq userLoginReq) throws Exception {
        return userService.login(userLoginReq);
    }

}

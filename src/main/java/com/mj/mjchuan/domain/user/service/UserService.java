package com.mj.mjchuan.domain.user.service;

import com.mj.mjchuan.presentation.req.UserLoginReq;

/**
 * @author xinruifan
 * @create 2025-01-07 17:33
 */
public interface UserService {

    String login(UserLoginReq userLoginReq);
}

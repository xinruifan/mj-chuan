package com.mj.mjchuan.domain.user.service.impl;

import com.mj.mjchuan.domain.user.model.UserInfo;
import com.mj.mjchuan.domain.user.service.UserService;
import com.mj.mjchuan.infrastructure.repository.UserRepository;
import com.mj.mjchuan.presentation.req.UserLoginReq;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author xinruifan
 * @create 2025-01-07 17:34
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;

    @Override
    public String login(UserLoginReq userLoginReq) {
        UserInfo byAccountAndPw = userRepository.findByAccountAndPw(userLoginReq.getAccount(), userLoginReq.getPassword());

        return null;
    }
}

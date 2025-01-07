package com.mj.mjchuan.domain.user.service.impl;

import cn.hutool.jwt.JWTUtil;
import com.mj.mjchuan.domain.user.model.UserInfo;
import com.mj.mjchuan.domain.user.service.UserService;
import com.mj.mjchuan.infrastructure.repository.UserRepository;
import com.mj.mjchuan.infrastructure.security.JwtTokenUtil;
import com.mj.mjchuan.infrastructure.security.UserJwt;
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
    public String login(UserLoginReq userLoginReq) throws Exception {
        UserInfo userInfo = userRepository.findByAccount(userLoginReq.getAccount());
        if(userInfo == null){
            userInfo = new UserInfo();
            userInfo.setAccount(userLoginReq.getAccount());
            userInfo.setPassword(userLoginReq.getPassword());
            userRepository.save(userInfo);
        }else if(!userInfo.getPassword().equals(userLoginReq.getPassword())){
            throw new Exception("账号密码不匹配，忘记密码可直接使用新账号直接登录即可");
        }
        UserJwt userJwt = UserJwt.builder().account(userInfo.getAccount()).id(userInfo.getId()).build();
        return JwtTokenUtil.generateToken(userJwt);
    }
}

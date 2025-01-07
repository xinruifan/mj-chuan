package com.mj.mjchuan.infrastructure.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mj.mjchuan.domain.user.model.UserInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author xinruifan
 * @create 2025-01-07 17:37
 */
@Mapper
public interface UserMapper extends BaseMapper<UserInfo> {


}

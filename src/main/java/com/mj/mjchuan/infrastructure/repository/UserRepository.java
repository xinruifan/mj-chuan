package com.mj.mjchuan.infrastructure.repository;

import com.mj.mjchuan.domain.user.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author xinruifan
 * @create 2025-01-07 17:40
 */
@Repository
public interface UserRepository extends JpaRepository<UserInfo, Long> {


    @Query(value = "select * from user_info where account = ?1", nativeQuery = true)
    UserInfo findByAccount(String account);
}

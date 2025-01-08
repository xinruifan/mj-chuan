package com.mj.mjchuan.infrastructure.repository;

import com.mj.mjchuan.domain.mapping.model.UserGameRoomMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author xinruifan
 * @create 2025-01-07 17:40
 */
@Repository
public interface UserGameRoomRepository extends JpaRepository<UserGameRoomMapping, Long> {

}

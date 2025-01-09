package com.mj.mjchuan.infrastructure.repository;

import com.mj.mjchuan.domain.mapping.model.UserGameRoomMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xinruifan
 * @create 2025-01-07 17:40
 */
@Repository
public interface UserGameRoomRepository extends JpaRepository<UserGameRoomMapping, Long> {

    @Query(value = "select * from user_game_room_mapping where room_id = ?1", nativeQuery = true)
    List<UserGameRoomMapping> findByRoomId(Long roomId);

    @Modifying
    @Query(value = "delete from user_game_room_mapping where room_id = ?1 and user_id =?2", nativeQuery = true)
    void deleteByRoomAndUser(Long roomId, Long userId);
}

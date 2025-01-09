package com.mj.mjchuan.infrastructure.repository;

import com.mj.mjchuan.domain.game.model.GameRound;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author xinruifan
 * @create 2025-01-07 17:40
 */
@Repository
public interface GameRoundRepository extends JpaRepository<GameRound, Long> {

    @Query(value = "select max(round_count) from game_round where room_id = ?1", nativeQuery = true)
    Integer queryMaxRoundByRoom(Long roomId);
}

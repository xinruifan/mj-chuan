package com.mj.mjchuan.domain.game.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author xinruifan
 * @create 2025-01-07 19:05
 */

@Data
@Entity
@Table(name = "game_round")
public class GameRound {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long roundCount;

    // wait begin end
    private String state;

    private LocalDateTime createTime;

}

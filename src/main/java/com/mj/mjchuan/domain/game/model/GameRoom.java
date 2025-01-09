package com.mj.mjchuan.domain.game.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author xinruifan
 * @create 2025-01-07 14:28
 */
@Data
@Entity
@Table(name = "game_room")
public class GameRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ruleId;

    private Integer totalRound;

    // wait begin end
    private String state;

    private LocalDateTime createTime;


}

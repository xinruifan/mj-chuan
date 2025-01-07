package com.mj.mjchuan.domain.user.model;

import lombok.Data;

import javax.persistence.*;

/**
 * @author xinruifan
 * @create 2025-01-07 19:02
 */
@Data
@Entity
@Table(name = "game_palyer_state")
public class GamePlayerState {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long roomId;

    private Long roundId;


}

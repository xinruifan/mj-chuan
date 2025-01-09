package com.mj.mjchuan.domain.mapping.model;

import lombok.Data;

import javax.persistence.*;

/**
 * @author xinruifan
 * @create 2025-01-07 18:56
 */
@Data
@Entity
@Table(name = "user_game_room_mapping")
public class UserGameRoomMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long roomId;

    private Long score;

    private Integer location;

    private boolean ready;

}

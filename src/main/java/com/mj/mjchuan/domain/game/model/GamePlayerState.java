package com.mj.mjchuan.domain.game.model;

import lombok.Data;

/**
 * @author xinruifan
 * @create 2025-01-07 19:02
 */
@Data
public class GamePlayerState {

    private Long userId;

    private String location;

    private String handCard;

    private String pengCard;

    private String gangCard;

    private String chuCard;

    private boolean win;

    private Long score;


}

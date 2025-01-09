package com.mj.mjchuan.domain.game.model;

import lombok.Data;

import java.util.List;

/**
 * @author xinruifan
 * @create 2025-01-07 19:02
 */
@Data
public class GamePlayerState {

    private Long userId;

    private String location;

    private List<Integer> handCard;

    private List<Integer> pengCard;

    private List<Integer> gangCard;

    private List<Integer> chuCard;

    private boolean win;

    private Long score;


}

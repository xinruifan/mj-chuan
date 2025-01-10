package com.mj.mjchuan.domain.game.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xinruifan
 * @create 2025-01-07 19:02
 */
@Data
public class GamePlayerState {

    private Long userId;

    private String location;

    private List<Integer> handCard = new ArrayList<>();

    private List<Integer> pengCard = new ArrayList<>();

    private List<Integer> gangCard = new ArrayList<>();

    private List<Integer> chuCard = new ArrayList<>();

    private boolean win;

    private Long score;


}

package com.mj.mjchuan.application.template;

import com.mj.mjchuan.domain.game.model.GamePlayerState;
import com.mj.mjchuan.domain.game.model.GameRound;

/**
 * @author xinruifan
 * @create 2025-01-10 10:53
 */
public interface DecisionTemplate {


    boolean canOwnExecute(GamePlayerState gamePlayerState,Integer cardKey);

    boolean canOtherExecute(GamePlayerState gamePlayerState, Integer cardKey);
}

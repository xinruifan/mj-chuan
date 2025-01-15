package com.mj.mjchuan.application.template;

import com.mj.mjchuan.domain.game.model.GamePlayerState;

/**
 * @author xinruifan
 * @create 2025-01-10 14:22
 */
public class AbstractDecisionTemplate implements DecisionTemplate{

    @Override
    public boolean canOwnExecute(GamePlayerState gamePlayerState, Integer cardKey) {
        return false;
    }

    @Override
    public boolean canOtherExecute(GamePlayerState gamePlayerState, Integer cardKey) {
        return false;
    }

    @Override
    public Long computeScore(GamePlayerState gamePlayerState, Integer cardKey) {
        return null;
    }
}

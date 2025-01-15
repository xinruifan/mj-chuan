package com.mj.mjchuan.application.handler;

import com.mj.mjchuan.application.template.HuDecisionTemplate;
import com.mj.mjchuan.domain.game.model.GamePlayerState;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author xinruifan
 * @create 2025-01-14 16:43
 */
@Component
public class HuCardHandler extends AbstractHandler{

    @Resource
    public HuDecisionTemplate huDecisionTemplate;

    @Override
    protected void handle(HandlerContext handlerContext) {
        GamePlayerState actionPlayerState = super.getByLocation(handlerContext.getGameRound(), handlerContext.getActionLocation());
        Long score = huDecisionTemplate.computeScore(actionPlayerState, handlerContext.getCardKey());
        super.modifyListElem(actionPlayerState.getHandCard(), handlerContext.getCardKey(), 1, true);
        actionPlayerState.setWin(true);
        actionPlayerState.setScore(actionPlayerState.getScore() + score);
        updatePlayerState(handlerContext, actionPlayerState);
        if(handlerContext.getSourceLocation() == null){
            if(!handlerContext.getGameRound().getGamePlayerStateN().getLocation().equals(handlerContext.getActionLocation().getDesc())){
                GamePlayerState gamePlayerState = handlerContext.getGameRound().getGamePlayerStateN();
                handlerContext.getGameRound().getGamePlayerStateN().setScore(gamePlayerState.getScore()-score);
            }
            if(!handlerContext.getGameRound().getGamePlayerStateE().getLocation().equals(handlerContext.getActionLocation().getDesc())){
                GamePlayerState gamePlayerState = handlerContext.getGameRound().getGamePlayerStateE();
                handlerContext.getGameRound().getGamePlayerStateE().setScore(gamePlayerState.getScore()-score);
            }
            if(!handlerContext.getGameRound().getGamePlayerStateW().getLocation().equals(handlerContext.getActionLocation().getDesc())){
                GamePlayerState gamePlayerState = handlerContext.getGameRound().getGamePlayerStateW();
                handlerContext.getGameRound().getGamePlayerStateW().setScore(gamePlayerState.getScore()-score);
            }
            if(!handlerContext.getGameRound().getGamePlayerStateS().getLocation().equals(handlerContext.getActionLocation().getDesc())){
                GamePlayerState gamePlayerState = handlerContext.getGameRound().getGamePlayerStateS();
                handlerContext.getGameRound().getGamePlayerStateS().setScore(gamePlayerState.getScore()-score);
            }
        }else{
            GamePlayerState sourcePlayerState = super.getByLocation(handlerContext.getGameRound(), handlerContext.getSourceLocation());
            sourcePlayerState.setScore(sourcePlayerState.getScore() - score);
            updatePlayerState(handlerContext, sourcePlayerState);
        }
        super.updateGameRound(handlerContext.getGameRound());
        super.addRecord(handlerContext.getGameRound(), HandleActionEnum.HU);
    }

    @Override
    protected void nextCanHandle(HandlerContext handlerContext) throws Exception {

    }
}

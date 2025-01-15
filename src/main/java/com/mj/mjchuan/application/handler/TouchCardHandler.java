package com.mj.mjchuan.application.handler;

import com.mj.mjchuan.domain.dto.PlayerCanMsg;
import com.mj.mjchuan.domain.dto.RespActionEnum;
import com.mj.mjchuan.domain.dto.WsSendMsgDTO;
import com.mj.mjchuan.domain.game.model.GamePlayerState;
import com.mj.mjchuan.domain.game.model.GameRound;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * @author xinruifan
 * @create 2025-01-09 18:44
 */
@Component
public class TouchCardHandler extends AbstractHandler {


    @Override
    public boolean canHandle(HandlerContext handlerContext) {
        List<Integer> wallCard = handlerContext.getGameRound().getWallCard();
        if (CollectionUtils.isEmpty(wallCard)) {
            return false;
        }
        return true;
    }

    @Override
    public void doNotCanHandle(HandlerContext handlerContext) throws Exception {
        gameRoundAgg.createGameRound(handlerContext.getGameRound().getRoomId());
    }

    @Override
    protected void handle(HandlerContext handlerContext) {
        GameRound gameRound = handlerContext.getGameRound();
        GamePlayerState gamePlayerState = super.getByLocation(handlerContext.getGameRound(),handlerContext.getActionLocation());
        Integer cardKey = gameRound.getWallCard().remove(0);
        handlerContext.setCardKey(cardKey);
        gamePlayerState.getHandCard().add(cardKey);
        Collections.sort(gamePlayerState.getHandCard());
        updatePlayerState(handlerContext, gamePlayerState);
        super.updateGameRound(handlerContext.getGameRound());
        super.addRecord(handlerContext.getGameRound(), HandleActionEnum.TOUCH);

    }

    @Override
    protected void nextCanHandle(HandlerContext handlerContext) {
        GamePlayerState gamePlayerState = super.getByLocation(handlerContext.getGameRound(),handlerContext.getActionLocation());
        boolean hu = huDecisionTemplate.canOwnExecute(gamePlayerState, handlerContext.getCardKey());
        boolean gang = gangDecisionTemplate.canOwnExecute(gamePlayerState, handlerContext.getCardKey());
        PlayerCanMsg msg = PlayerCanMsg.builder().chu(true).hu(hu).pen(false).gang(gang).isOneself(true)
                .roundId(handlerContext.getGameRound().getId()).keyCard(handlerContext.getCardKey()).build();
        WsSendMsgDTO wsMsg = WsSendMsgDTO.builder().action(RespActionEnum.NEXT_HANDLE.toString()).obj(msg).build();
        handlerContext.getMsgMap().put(gamePlayerState.getUserId(),wsMsg);
    }

}

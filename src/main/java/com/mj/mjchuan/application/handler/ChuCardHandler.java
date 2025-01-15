package com.mj.mjchuan.application.handler;

import com.mj.mjchuan.domain.dto.PlayerCanMsg;
import com.mj.mjchuan.domain.dto.RespActionEnum;
import com.mj.mjchuan.domain.dto.WsSendMsgDTO;
import com.mj.mjchuan.domain.game.enums.RoomLocationEnum;
import com.mj.mjchuan.domain.game.model.GamePlayerState;
import com.mj.mjchuan.domain.game.model.GameRound;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Map;
import java.util.UUID;

/**
 * @author xinruifan
 * @create 2025-01-10 11:53
 */
@Component
public class ChuCardHandler extends AbstractHandler {

    @Resource
    private TouchCardHandler touchCardHandler;

    @Override
    protected void handle(HandlerContext handlerContext) {
        GamePlayerState gamePlayerState = super.getByLocation(handlerContext.getGameRound(),handlerContext.getActionLocation());
        handlerContext.setUuid(UUID.randomUUID().toString());
        Integer cardKey = handlerContext.getCardKey();
        super.modifyListElem(gamePlayerState.getHandCard(), cardKey, 1, false);
        super.modifyListElem(gamePlayerState.getChuCard(), cardKey, 1, true);
        updatePlayerState(handlerContext, gamePlayerState);
        super.updateGameRound(handlerContext.getGameRound());
        super.addRecord(handlerContext.getGameRound(), HandleActionEnum.CHU);
    }

    @Override
    protected void nextCanHandle(HandlerContext handlerContext) throws Exception {
        //观测其余人操作
        RoomLocationEnum actionLocation = handlerContext.getActionLocation();
        GameRound gameRound = handlerContext.getGameRound();
        for (RoomLocationEnum roomLocationEnum : RoomLocationEnum.values()) {
            if (roomLocationEnum == actionLocation) {
                continue;
            }
            if (roomLocationEnum == RoomLocationEnum.EAST) {
                GamePlayerState gamePlayerStateE = gameRound.getGamePlayerStateE();
                observeOtherHandler(handlerContext, gamePlayerStateE);
            }
            if (roomLocationEnum == RoomLocationEnum.NORTH) {
                GamePlayerState gamePlayerStateN = gameRound.getGamePlayerStateN();
                observeOtherHandler(handlerContext, gamePlayerStateN);
            }
            if (roomLocationEnum == RoomLocationEnum.WEST) {
                GamePlayerState gamePlayerStateW = gameRound.getGamePlayerStateW();
                observeOtherHandler(handlerContext, gamePlayerStateW);
            }
            if (roomLocationEnum == RoomLocationEnum.SOUTH) {
                GamePlayerState gamePlayerStateS = gameRound.getGamePlayerStateS();
                observeOtherHandler(handlerContext, gamePlayerStateS);
            }
        }
        Map<Long, WsSendMsgDTO> msgMap = handlerContext.getMsgMap();
        if (CollectionUtils.isEmpty(msgMap)) {
            RoomLocationEnum roomLocationEnum = super.nextTouchLocation(handlerContext.getActionLocation());
            handlerContext.setActionLocation(roomLocationEnum);
            touchCardHandler.handleRequest(handlerContext);
        } else {
            eventLatchManager.createEvent(handlerContext.getUuid(), msgMap.size());
        }
    }

    private void observeOtherHandler(HandlerContext handlerContext, GamePlayerState gamePlayerState) {
        GamePlayerState actionPlayer = super.getByLocation(handlerContext.getGameRound(),handlerContext.getActionLocation());

        boolean hu = huDecisionTemplate.canOtherExecute(gamePlayerState, handlerContext.getCardKey());
        boolean pen = penDecisionTemplate.canOtherExecute(gamePlayerState, handlerContext.getCardKey());
        boolean gang = gangDecisionTemplate.canOtherExecute(gamePlayerState, handlerContext.getCardKey());
        if (hu || pen || gang) {
            PlayerCanMsg msg = PlayerCanMsg.builder().chu(false).hu(hu).pen(pen).gang(gang).isOneself(false).roundId(handlerContext.getGameRound().getId())
                    .keyCardSource(actionPlayer.getUserId()).keyCard(handlerContext.getCardKey()).uuid(handlerContext.getUuid()).build();
            WsSendMsgDTO wsMsg = WsSendMsgDTO.builder().action(RespActionEnum.NEXT_HANDLE.toString()).obj(msg).build();
            handlerContext.getMsgMap().put(gamePlayerState.getUserId(), wsMsg);
        }
    }


}

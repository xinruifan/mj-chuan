package com.mj.mjchuan.application.handler;

import com.mj.mjchuan.application.cache.EventLatchManager;
import com.mj.mjchuan.application.service.GameRoundAgg;
import com.mj.mjchuan.application.template.GangDecisionTemplate;
import com.mj.mjchuan.application.template.HuDecisionTemplate;
import com.mj.mjchuan.application.template.PenDecisionTemplate;
import com.mj.mjchuan.domain.dto.WsSendMsgDTO;
import com.mj.mjchuan.domain.game.enums.RoomLocationEnum;
import com.mj.mjchuan.domain.game.model.GamePlayerState;
import com.mj.mjchuan.domain.game.model.GameRound;
import com.mj.mjchuan.domain.record.RecordInfo;
import com.mj.mjchuan.infrastructure.repository.GameRecordInfoRepository;
import com.mj.mjchuan.infrastructure.repository.GameRoundRepository;
import com.mj.mjchuan.infrastructure.socketHander.WebSocketSendMsg;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author xinruifan
 * @create 2025-01-09 18:42
 */
public abstract class AbstractHandler{

    @Resource
    public GameRoundAgg gameRoundAgg;
    @Resource
    public GameRoundRepository gameRoundRepository;
    @Resource
    public GameRecordInfoRepository gameRecordInfoRepository;
    @Resource
    public PenDecisionTemplate penDecisionTemplate;
    @Resource
    public HuDecisionTemplate huDecisionTemplate;
    @Resource
    public GangDecisionTemplate gangDecisionTemplate;
    @Resource
    public WebSocketSendMsg webSocketSendMsg;
    @Resource
    public EventLatchManager eventLatchManager;


    public void handleRequest(HandlerContext handlerContext) throws Exception {

        boolean canHandle = canHandle(handlerContext);
        if (!canHandle) {
            doNotCanHandle(handlerContext);
            return;
        }

        handle(handlerContext);

        nextCanHandle(handlerContext);

        sendMsg(handlerContext);
    }


    public void doNotCanHandle(HandlerContext handlerContext) throws Exception {
    }

    public boolean canHandle(HandlerContext handlerContext) {
        return true;
    }

    protected abstract void handle(HandlerContext handlerContext);

    protected abstract void nextCanHandle(HandlerContext handlerContext) throws Exception;


    protected void sendMsg(HandlerContext handlerContext) throws Exception {
        Map<Long, WsSendMsgDTO> msgMap = handlerContext.getMsgMap();
        if (!CollectionUtils.isEmpty(msgMap)) {
            for (Long userId : msgMap.keySet()) {
                webSocketSendMsg.sendTextMessage(msgMap.get(userId), Collections.singletonList(userId));
            }
        }
    }

    public GamePlayerState getByLocation(HandlerContext handlerContext) {

        switch (handlerContext.getActionLocation()) {
            case EAST:
                return handlerContext.getGameRound().getGamePlayerStateE();
            case NORTH:
                return handlerContext.getGameRound().getGamePlayerStateN();
            case WEST:
                return handlerContext.getGameRound().getGamePlayerStateW();
            case SOUTH:
                return handlerContext.getGameRound().getGamePlayerStateS();
            default:
                return null;
        }
    }

    public void updatePlayerState(HandlerContext handlerContext, GamePlayerState gamePlayerState) {

        switch (handlerContext.getActionLocation()) {
            case EAST:
                handlerContext.getGameRound().setGamePlayerStateE(gamePlayerState);
                break;
            case NORTH:
                handlerContext.getGameRound().setGamePlayerStateN(gamePlayerState);
                break;
            case WEST:
                handlerContext.getGameRound().setGamePlayerStateW(gamePlayerState);
                break;
            case SOUTH:
                handlerContext.getGameRound().setGamePlayerStateS(gamePlayerState);
                break;
            default:
        }
    }

    protected void updateGameRound(GameRound gameRound) {
        gameRoundRepository.save(gameRound);
    }

    protected void addRecord(GameRound gameRound, HandleActionEnum handleActionEnum) {
        RecordInfo recordInfo = RecordInfo.builder()
                .gameRound(gameRound)
                .roundCount(gameRound.getRoundCount())
                .handleAction(handleActionEnum.toString())
                .roomId(gameRound.getRoomId())
                .build();
        gameRecordInfoRepository.save(recordInfo);
    }

    protected void modifyListElem(List<Integer> list, Integer keyCard, Integer count, boolean add) {
        if (add) {
            for (int i = 0; i < count; i++) {
                list.add(keyCard);
            }
        } else {
            int removed = 0;
            Iterator<Integer> iterator = list.iterator();
            while (iterator.hasNext() && removed < count) {
                if (iterator.next().equals(keyCard)) {
                    iterator.remove();
                    removed++;
                }
            }
        }
        Collections.sort(list);
    }


    protected RoomLocationEnum nextTouchLocation(RoomLocationEnum roomLocationEnum){
        switch (roomLocationEnum) {
            case EAST:
                return RoomLocationEnum.NORTH;
            case NORTH:
                return RoomLocationEnum.WEST;
            case WEST:
                return RoomLocationEnum.SOUTH;
            case SOUTH:
                return RoomLocationEnum.EAST;
            default:
                return null;
        }
    }

}

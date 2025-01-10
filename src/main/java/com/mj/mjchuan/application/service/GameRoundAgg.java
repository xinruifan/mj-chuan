package com.mj.mjchuan.application.service;

import com.mj.mjchuan.application.handler.ChuCardHandler;
import com.mj.mjchuan.application.handler.HandleActionEnum;
import com.mj.mjchuan.application.handler.HandlerContext;
import com.mj.mjchuan.application.handler.TouchCardHandler;
import com.mj.mjchuan.application.manager.CardManager;
import com.mj.mjchuan.domain.dto.RespActionEnum;
import com.mj.mjchuan.domain.dto.WsSendMsgDTO;
import com.mj.mjchuan.domain.game.enums.RoomLocationEnum;
import com.mj.mjchuan.domain.game.enums.RoomStateEnum;
import com.mj.mjchuan.domain.game.model.GameRoom;
import com.mj.mjchuan.domain.game.model.GameRound;
import com.mj.mjchuan.domain.mapping.model.UserGameRoomMapping;
import com.mj.mjchuan.domain.record.RecordInfo;
import com.mj.mjchuan.infrastructure.context.ReqContext;
import com.mj.mjchuan.infrastructure.repository.GameRecordInfoRepository;
import com.mj.mjchuan.infrastructure.repository.GameRoomRepository;
import com.mj.mjchuan.infrastructure.repository.GameRoundRepository;
import com.mj.mjchuan.infrastructure.repository.UserGameRoomRepository;
import com.mj.mjchuan.infrastructure.socketHander.WebSocketSendMsg;
import com.mj.mjchuan.presentation.req.GamePlayerActionReq;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xinruifan
 * @create 2025-01-09 14:57
 */
@Component
public class GameRoundAgg {

    @Resource
    private UserGameRoomRepository userGameRoomRepository;
    @Resource
    private GameRoomRepository gameRoomRepository;
    @Resource
    private GameRoundRepository gameRoundRepository;
    @Resource
    private WebSocketSendMsg webSocketSendMsg;
    @Resource
    private TouchCardHandler touchCardHandler;
    @Resource
    private ChuCardHandler chuCardHandler;
    @Resource
    private GameRecordInfoRepository gameRecordInfoRepository;


    public void createGameRound(Long roomId) throws Exception {
        GameRound gameRound = new GameRound();
        GameRoom gameRoom = gameRoomRepository.findById(roomId).orElseThrow(() -> new Exception("房间不存在"));
        Integer lastRound = gameRoundRepository.queryMaxRoundByRoom(roomId);
        List<UserGameRoomMapping> userGameRoomMappings = userGameRoomRepository.findByRoomId(roomId);
        List<Long> userIds = userGameRoomMappings.stream().map(UserGameRoomMapping::getUserId).collect(Collectors.toList());
        if (lastRound.equals(gameRoom.getTotalRound())) {
            //满局 结束
            gameRoom.setState(RoomStateEnum.END.toString());
            gameRoomRepository.save(gameRoom);
            userIds.forEach(x -> userGameRoomRepository.deleteByRoomAndUser(roomId, x));
            WsSendMsgDTO wsSendMsgDTO = new WsSendMsgDTO();
            wsSendMsgDTO.setAction(RespActionEnum.DISSUADE_PLAYER.toString());
            webSocketSendMsg.sendTextMessage(wsSendMsgDTO, userIds);
            return;
        }
        //发
        CardManager.createDeck(gameRound);
        for (UserGameRoomMapping userGameRoomMapping : userGameRoomMappings) {
            if (userGameRoomMapping.getLocation().equals(RoomLocationEnum.EAST.getCode())) {
                gameRound.getGamePlayerStateE().setLocation(RoomLocationEnum.EAST.getDesc());
                gameRound.getGamePlayerStateE().setUserId(userGameRoomMapping.getUserId());
            }
            if (userGameRoomMapping.getLocation().equals(RoomLocationEnum.SOUTH.getCode())) {
                gameRound.getGamePlayerStateS().setLocation(RoomLocationEnum.SOUTH.getDesc());
                gameRound.getGamePlayerStateS().setUserId(userGameRoomMapping.getUserId());
            }
            if (userGameRoomMapping.getLocation().equals(RoomLocationEnum.WEST.getCode())) {
                gameRound.getGamePlayerStateW().setLocation(RoomLocationEnum.WEST.getDesc());
                gameRound.getGamePlayerStateW().setUserId(userGameRoomMapping.getUserId());
            }
            if (userGameRoomMapping.getLocation().equals(RoomLocationEnum.NORTH.getCode())) {
                gameRound.getGamePlayerStateN().setLocation(RoomLocationEnum.NORTH.getDesc());
                gameRound.getGamePlayerStateN().setUserId(userGameRoomMapping.getUserId());
            }
        }
        RoomLocationEnum actionLocation = RoomLocationEnum.getRandomLocation();
        RecordInfo recordInfo = RecordInfo.builder()
                .gameRound(gameRound)
                .roundCount(gameRound.getRoundCount())
                .handleAction(HandleActionEnum.START.toString())
                .roomId(gameRound.getRoomId())
                .build();
        gameRecordInfoRepository.save(recordInfo);

        notifyPlayer(gameRound);

        HandlerContext handlerContext = new HandlerContext();
        handlerContext.setGameRound(gameRound);
        handlerContext.setActionLocation(actionLocation);
        touchCardHandler.handleRequest(handlerContext);
    }

    private void notifyPlayer(GameRound gameRound) throws Exception {
        WsSendMsgDTO wsSendMsgDTO = new WsSendMsgDTO();
        wsSendMsgDTO.setAction(RespActionEnum.INIT_PLAYER_CARD.toString());

        wsSendMsgDTO.setObj(gameRound.getGamePlayerStateE());
        webSocketSendMsg.sendTextMessage(wsSendMsgDTO, Collections.singletonList(gameRound.getGamePlayerStateE().getUserId()));

        wsSendMsgDTO.setObj(gameRound.getGamePlayerStateN());
        webSocketSendMsg.sendTextMessage(wsSendMsgDTO, Collections.singletonList(gameRound.getGamePlayerStateN().getUserId()));

        wsSendMsgDTO.setObj(gameRound.getGamePlayerStateS());
        webSocketSendMsg.sendTextMessage(wsSendMsgDTO, Collections.singletonList(gameRound.getGamePlayerStateS().getUserId()));

        wsSendMsgDTO.setObj(gameRound.getGamePlayerStateW());
        webSocketSendMsg.sendTextMessage(wsSendMsgDTO, Collections.singletonList(gameRound.getGamePlayerStateW().getUserId()));
    }


    public void chuCard(GamePlayerActionReq gamePlayerActionReq) throws Exception {
        Long userId = ReqContext.getUserId();
        GameRound gameRound = gameRoundRepository.findById(gamePlayerActionReq.getRoundId()).orElse(null);

        RoomLocationEnum actionLocation = getLocationByUser(userId, gameRound);
        HandlerContext handlerContext = new HandlerContext();
        handlerContext.setGameRound(gameRound);
        handlerContext.setCardKey(gamePlayerActionReq.getKeyCard());

        handlerContext.setActionLocation(actionLocation);
        chuCardHandler.handleRequest(handlerContext);
    }

    public RoomLocationEnum getLocationByUser(Long userId, GameRound gameRound) {
        if (gameRound.getGamePlayerStateW().getUserId().equals(userId)) {
            return RoomLocationEnum.WEST;
        }
        if (gameRound.getGamePlayerStateE().getUserId().equals(userId)) {
            return RoomLocationEnum.EAST;
        }
        if (gameRound.getGamePlayerStateN().getUserId().equals(userId)) {
            return RoomLocationEnum.NORTH;
        }
        if (gameRound.getGamePlayerStateS().getUserId().equals(userId)) {
            return RoomLocationEnum.SOUTH;
        }
        return null;
    }
}

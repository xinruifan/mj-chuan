package com.mj.mjchuan.application.socket;

import com.mj.mjchuan.domain.dto.RespActionEnum;
import com.mj.mjchuan.domain.dto.WsSendMsgDTO;
import com.mj.mjchuan.domain.game.service.GameRoundService;
import com.mj.mjchuan.domain.game.service.RoomService;
import com.mj.mjchuan.domain.mapping.model.UserGameRoomMapping;
import com.mj.mjchuan.infrastructure.context.ReqContext;
import com.mj.mjchuan.infrastructure.socketHander.WebSocketSendMsg;
import com.mj.mjchuan.presentation.req.CreateRoomReq;
import com.mj.mjchuan.presentation.req.ReadyPlayerReq;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xinruifan
 * @create 2025-01-09 10:31
 */
@Component
public class WebSocketAgg {

    @Resource
    private WebSocketSendMsg webSocketSendMsg;

    @Resource
    private RoomService roomService;

    @Resource
    private GameRoundService gameRoundService;


    public void handleCreateRoom(CreateRoomReq req) throws Exception {
        Long userId = ReqContext.getUserId();
        Long roomId = roomService.createRoom(req, userId);

        WsSendMsgDTO wsSendMsgDTO = new WsSendMsgDTO();
        wsSendMsgDTO.setAction(RespActionEnum.CREATE_ROOM_SUCCESS.toString());
        wsSendMsgDTO.setObj(roomId);
        webSocketSendMsg.sendTextMessage(wsSendMsgDTO, Collections.singletonList(userId));
    }


    public void handleJoinRoom(Long roomId) throws Exception {
        Long userId = ReqContext.getUserId();
        List<UserGameRoomMapping> userGameRoomMappings = roomService.joinRoom(roomId, userId);

        WsSendMsgDTO wsSendMsgDTO = new WsSendMsgDTO();
        wsSendMsgDTO.setAction(RespActionEnum.REFRESH_ROOM_PLAYER.toString());
        wsSendMsgDTO.setObj(userGameRoomMappings);
        webSocketSendMsg.sendTextMessage(wsSendMsgDTO, Collections.singletonList(userId));
    }

    public void handleLeaveRoom(Long roomId) throws Exception {
        Long userId = ReqContext.getUserId();
        List<UserGameRoomMapping> userGameRoomMappings = roomService.leaveRoom(roomId, userId);

        webSocketSendMsg.close(Collections.singletonList(userId));
        if (CollectionUtils.isEmpty(userGameRoomMappings)) {
            return;
        }

        WsSendMsgDTO wsSendMsgDTO = new WsSendMsgDTO();
        wsSendMsgDTO.setAction(RespActionEnum.REFRESH_ROOM_PLAYER.toString());
        wsSendMsgDTO.setObj(userGameRoomMappings);
        List<Long> userIds = userGameRoomMappings.stream().map(UserGameRoomMapping::getUserId).collect(Collectors.toList());
        webSocketSendMsg.sendTextMessage(wsSendMsgDTO, userIds);
    }


    public void handleReady(ReadyPlayerReq req) throws Exception {
        Long userId = ReqContext.getUserId();
        List<UserGameRoomMapping> userGameRoomMappings = roomService.ready(req, userId);

        WsSendMsgDTO wsSendMsgDTO = new WsSendMsgDTO();
        wsSendMsgDTO.setAction(RespActionEnum.REFRESH_ROOM_PLAYER.toString());
        wsSendMsgDTO.setObj(userGameRoomMappings);
        List<Long> userIds = userGameRoomMappings.stream().map(UserGameRoomMapping::getUserId).collect(Collectors.toList());
        webSocketSendMsg.sendTextMessage(wsSendMsgDTO, userIds);

        if (userGameRoomMappings.size() == 4) {
            boolean allReady = userGameRoomMappings.stream()
                    .allMatch(UserGameRoomMapping::isReady);
            if (allReady) {
                //建轮
                gameRoundService.createGameRound(req.getRoomId());
            }
        }
    }
}

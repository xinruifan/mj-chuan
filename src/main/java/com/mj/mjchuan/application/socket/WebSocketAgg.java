package com.mj.mjchuan.application.socket;

import com.mj.mjchuan.domain.dto.RespActionEnum;
import com.mj.mjchuan.domain.dto.WsSendMsgDTO;
import com.mj.mjchuan.domain.game.service.RoomService;
import com.mj.mjchuan.infrastructure.context.ReqContext;
import com.mj.mjchuan.infrastructure.socketHander.WebSocketSendMsg;
import com.mj.mjchuan.presentation.req.CreateRoomReq;
import com.sun.tools.javac.util.List;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

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


    public void handleCreateRoom(CreateRoomReq req) throws Exception {
        Long userId = ReqContext.getUserId();
        Long roomId = roomService.createRoom(req, userId);
        WsSendMsgDTO wsSendMsgDTO = new WsSendMsgDTO();
        wsSendMsgDTO.setAction(RespActionEnum.CREATE_ROOM_SUCCESS.toString());
        wsSendMsgDTO.setObj(roomId);
        webSocketSendMsg.sendTextMessage(wsSendMsgDTO, List.of(userId));
    }


    public void handleJoinRoom(Long roomId) throws Exception {
        Long userId = ReqContext.getUserId();
        roomService.joinRoom(roomId, userId);
    }

    public void handleLeaveRoom(Long roomId) throws Exception {
        Long userId = ReqContext.getUserId();
        roomService.leaveRoom(roomId, userId);
    }
}

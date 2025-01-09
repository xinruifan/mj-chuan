package com.mj.mjchuan.infrastructure.socketHander;

import cn.hutool.json.JSONObject;
import com.mj.mjchuan.application.socket.WebSocketAgg;
import com.mj.mjchuan.presentation.req.CreateRoomReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.Resource;

/**
 * @author xinruifan
 * @create 2025-01-08 18:49
 */
@Slf4j
public class WebSocketHandler extends AbstractWebSocketHandler {


    @Resource
    private WebSocketAgg webSocketAgg;

    // 处理接收到的文本消息
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        try {
            // 处理客户端发来的消息
            JSONObject msg = new JSONObject(message.getPayload());
            String action = msg.getStr("action");
            Object obj = msg.getObj("obj");
            switch (action) {
                case "createRoom":
                    webSocketAgg.handleCreateRoom((CreateRoomReq) obj);
                    break;
                case "joinRoom":
                    webSocketAgg.handleJoinRoom((Long) obj);
                    break;
                case "leaveRoom":
                    webSocketAgg.handleLeaveRoom((Long) obj);
                    break;
                default:
                    session.sendMessage(new TextMessage("{\"status\": \"error\", \"message\": \"Unknown action\"}"));
            }
        } catch (Exception e) {
            log.error("Error processing message: " + e.getMessage());
            session.sendMessage(new TextMessage("{\"status\": \"error\", \"message\": \"An error occurred while processing your message.\"}"));
            session.close(CloseStatus.SERVER_ERROR);
        }
    }



}

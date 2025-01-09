package com.mj.mjchuan.infrastructure.socketHander;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xinruifan
 * @create 2025-01-09 10:22
 */
public abstract class AbstractWebSocketHandler extends TextWebSocketHandler {

    public Map<Long, WebSocketSession> sessions = new ConcurrentHashMap<>();


    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        Long userId = (Long) session.getAttributes().get("userId");
        sessions.put(userId, session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        // 清理连接
        sessions.entrySet().removeIf(entry -> entry.getValue().equals(session));
        System.out.println("Player disconnected");
    }

}

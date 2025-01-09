package com.mj.mjchuan.infrastructure.config;

import com.mj.mjchuan.infrastructure.socketHander.WebSocketHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * @author xinruifan
 * @create 2025-01-08 18:30
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // 注册 WebSocket 处理器，监听 "/ws" 端点
        registry.addHandler(new WebSocketHandler(), "/mjws").setAllowedOrigins("*");
    }


}

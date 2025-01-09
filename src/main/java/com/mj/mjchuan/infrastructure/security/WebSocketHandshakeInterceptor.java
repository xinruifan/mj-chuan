package com.mj.mjchuan.infrastructure.security;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Component
public class WebSocketHandshakeInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        // 获取 Authorization 头
        String authorizationHeader = request.getHeaders().getFirst("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);  // 提取 Bearer Token

            // 解析 JWT 并获取用户信息
            UserJwt userJwt = JwtTokenUtil.parseJwtUser(token);

            if (userJwt != null) {
                // 将 userId 存入 WebSocketSession 的属性中，后续可以通过 wsHandler.getAttributes() 获取
                attributes.put("userId", userJwt.getId());
                SecurityContextHolder.getContext().setAuthentication(new JwtAuthenticationToken(userJwt));
                return true;
            } else {
                // 如果 Token 无效或解析失败，拒绝握手
                response.setStatusCode(org.springframework.http.HttpStatus.UNAUTHORIZED);
                return false;
            }
        } else {
            // 如果没有 Authorization 头，拒绝握手
            response.setStatusCode(org.springframework.http.HttpStatus.UNAUTHORIZED);
            return false;
        }
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
}

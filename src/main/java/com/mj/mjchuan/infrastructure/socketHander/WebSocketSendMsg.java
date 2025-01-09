package com.mj.mjchuan.infrastructure.socketHander;

import cn.hutool.json.JSONUtil;
import com.mj.mjchuan.domain.dto.WsSendMsgDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;

/**
 * @author xinruifan
 * @create 2025-01-09 11:35
 */
@Slf4j
@Component
public class WebSocketSendMsg extends AbstractWebSocketHandler{

    public void sendTextMessage(WsSendMsgDTO wsSendMsgDTO, List<Long> userId) throws Exception {
        if (userId == null || userId.isEmpty()) {
            // 群发消息
            for (WebSocketSession session : sessions.values()) {
                if (session.isOpen()) {
                    String msg = JSONUtil.toJsonStr(wsSendMsgDTO);
                    session.sendMessage(new TextMessage(msg));
                    log.info("send msg:{}", msg);
                }
            }
        } else {
            // 给指定的用户发送消息
            for (Long id : userId) {
                WebSocketSession session = sessions.get(id);
                if (session != null && session.isOpen()) {
                    String msg = JSONUtil.toJsonStr(wsSendMsgDTO);
                    session.sendMessage(new TextMessage(msg));
                    log.info("send msg:{}", msg);
                }
            }
        }
    }
}

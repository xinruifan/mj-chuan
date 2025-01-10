package com.mj.mjchuan.application.handler;

import com.mj.mjchuan.domain.dto.WsSendMsgDTO;
import com.mj.mjchuan.domain.game.enums.RoomLocationEnum;
import com.mj.mjchuan.domain.game.model.GameRound;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xinruifan
 * @create 2025-01-10 13:21
 */
@Data
public class HandlerContext {

    private GameRound gameRound;


    private RoomLocationEnum actionLocation;

    private Integer cardKey;

    private String uuid;

    private Map<Long, WsSendMsgDTO> msgMap = new HashMap<>();
}

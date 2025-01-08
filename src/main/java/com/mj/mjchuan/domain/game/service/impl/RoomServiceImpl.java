package com.mj.mjchuan.domain.game.service.impl;

import cn.hutool.json.JSONUtil;
import com.mj.mjchuan.domain.game.enums.RoomLocationEnum;
import com.mj.mjchuan.domain.game.model.GameRoom;
import com.mj.mjchuan.domain.game.service.RoomService;
import com.mj.mjchuan.domain.mapping.model.UserGameRoomMapping;
import com.mj.mjchuan.infrastructure.repository.RoomRepository;
import com.mj.mjchuan.infrastructure.repository.UserGameRoomRepository;
import com.mj.mjchuan.presentation.req.CreateRoomReq;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @author xinruifan
 * @create 2025-01-08 11:46
 */
@Service
public class RoomServiceImpl implements RoomService {

    @Resource
    private RoomRepository roomRepository;
    @Resource
    private UserGameRoomRepository userGameRoomRepository;


    @Override
    public Long createRoom(CreateRoomReq createRoomReq, Long userId) {
        GameRoom gameRoom = new GameRoom();
        gameRoom.setRuleId(JSONUtil.toJsonStr(createRoomReq.getRuleId()));
        gameRoom.setState("wait");
        gameRoom.setTotalRound(createRoomReq.getTotalRound());
        gameRoom.setCreateTime(LocalDateTime.now());
        roomRepository.save(gameRoom);

        UserGameRoomMapping userGameRoomMapping = new UserGameRoomMapping();
        userGameRoomMapping.setRoomId(gameRoom.getId());
        userGameRoomMapping.setUserId(userId);
        userGameRoomMapping.setScore(0L);
        userGameRoomMapping.setLocation(RoomLocationEnum.EAST.getCode());
        userGameRoomRepository.save(userGameRoomMapping);
        return null;
    }
}

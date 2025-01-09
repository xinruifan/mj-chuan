package com.mj.mjchuan.domain.game.service;

import com.mj.mjchuan.domain.mapping.model.UserGameRoomMapping;
import com.mj.mjchuan.presentation.req.CreateRoomReq;
import com.mj.mjchuan.presentation.req.ReadyPlayerReq;

import java.util.List;

/**
 * @author xinruifan
 * @create 2025-01-08 11:45
 */
public interface RoomService {


    Long createRoom(CreateRoomReq createRoomReq, Long userId);

    List<UserGameRoomMapping> joinRoom(Long roomId, Long userId) throws Exception;

    List<UserGameRoomMapping> leaveRoom(Long roomId, Long userId) throws Exception;

    List<UserGameRoomMapping> ready(ReadyPlayerReq req, Long userId);
}

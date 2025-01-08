package com.mj.mjchuan.domain.game.service;

import com.mj.mjchuan.presentation.req.CreateRoomReq;

/**
 * @author xinruifan
 * @create 2025-01-08 11:45
 */
public interface RoomService {


    Long createRoom(CreateRoomReq createRoomReq, Long userId);
}

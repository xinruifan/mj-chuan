package com.mj.mjchuan.presentation.controller;

import com.mj.mjchuan.domain.game.service.RoomService;
import com.mj.mjchuan.infrastructure.context.ReqContext;
import com.mj.mjchuan.presentation.req.CreateRoomReq;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author xinruifan
 * @create 2025-01-08 11:31
 */

@RestController
public class GameRoomController {

    @Resource
    private RoomService roomService;

    @GetMapping(value = "/createRoom")
    public Long createRoom(CreateRoomReq createRoomReq) {
        Long userId = ReqContext.getUserId();
        return roomService.createRoom(createRoomReq,userId);
    }
}

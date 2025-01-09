package com.mj.mjchuan.presentation.controller;

import com.mj.mjchuan.domain.game.service.RoomService;
import com.mj.mjchuan.infrastructure.context.ReqContext;
import com.mj.mjchuan.presentation.req.CreateRoomReq;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author xinruifan
 * @create 2025-01-08 11:31
 */

@RestController
@RequestMapping("/webSocket")
public class GameRoomController {

    @Resource
    private RoomService roomService;

    @PostMapping(value = "/createRoom")
    public Long createRoom(@RequestBody CreateRoomReq createRoomReq) {
        Long userId = ReqContext.getUserId();
        return roomService.createRoom(createRoomReq,userId);
    }

    @GetMapping(value = "/joinRoom")
    public boolean joinRoom(@RequestParam("roomId") Long roomId) throws Exception {
        Long userId = ReqContext.getUserId();
        return roomService.joinRoom(roomId,userId);
    }

    @GetMapping(value = "/leaveRoom")
    public boolean leaveRoom(@RequestParam("roomId") Long roomId) throws Exception {
        Long userId = ReqContext.getUserId();
        return roomService.leaveRoom(roomId,userId);
    }

    @GetMapping(value = "/ready")
    public boolean ready(@RequestParam("roomId") Long roomId) throws Exception {
        Long userId = ReqContext.getUserId();
        return roomService.ready(roomId, userId);
    }
}

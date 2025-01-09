package com.mj.mjchuan.presentation.controller;

import com.mj.mjchuan.domain.game.service.RoomService;
import com.mj.mjchuan.domain.mapping.model.UserGameRoomMapping;
import com.mj.mjchuan.infrastructure.context.ReqContext;
import com.mj.mjchuan.presentation.req.CreateRoomReq;
import com.mj.mjchuan.presentation.req.ReadyPlayerReq;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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
    public List<UserGameRoomMapping> joinRoom(@RequestParam("roomId") Long roomId) throws Exception {
        Long userId = ReqContext.getUserId();
        return roomService.joinRoom(roomId,userId);
    }

    @GetMapping(value = "/leaveRoom")
    public List<UserGameRoomMapping> leaveRoom(@RequestParam("roomId") Long roomId) throws Exception {
        Long userId = ReqContext.getUserId();
        return roomService.leaveRoom(roomId,userId);
    }

    @PostMapping(value = "/ready")
    public List<UserGameRoomMapping> ready(@RequestBody ReadyPlayerReq req) throws Exception {
        Long userId = ReqContext.getUserId();
        return roomService.ready(req,userId);
    }
}

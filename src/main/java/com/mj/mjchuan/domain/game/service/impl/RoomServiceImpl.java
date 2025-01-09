package com.mj.mjchuan.domain.game.service.impl;

import cn.hutool.json.JSONUtil;
import com.mj.mjchuan.domain.game.enums.RoomLocationEnum;
import com.mj.mjchuan.domain.game.enums.RoomStateEnum;
import com.mj.mjchuan.domain.game.model.GameRoom;
import com.mj.mjchuan.domain.game.service.RoomService;
import com.mj.mjchuan.domain.mapping.model.UserGameRoomMapping;
import com.mj.mjchuan.infrastructure.repository.RoomRepository;
import com.mj.mjchuan.infrastructure.repository.UserGameRoomRepository;
import com.mj.mjchuan.presentation.req.CreateRoomReq;
import com.mj.mjchuan.presentation.req.ReadyPlayerReq;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

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

    private static final List<Integer> LOCATION = Arrays.asList(1, 2, 3, 4);


    @Override
    public Long createRoom(CreateRoomReq createRoomReq, Long userId) {
        GameRoom gameRoom = new GameRoom();
        gameRoom.setRuleId(JSONUtil.toJsonStr(createRoomReq.getRuleId()));
        gameRoom.setState(RoomStateEnum.WHIT.toString());
        gameRoom.setTotalRound(createRoomReq.getTotalRound());
        gameRoom.setCreateTime(LocalDateTime.now());
        roomRepository.save(gameRoom);

        UserGameRoomMapping userGameRoomMapping = new UserGameRoomMapping();
        userGameRoomMapping.setRoomId(gameRoom.getId());
        userGameRoomMapping.setUserId(userId);
        userGameRoomMapping.setScore(0L);
        userGameRoomMapping.setLocation(RoomLocationEnum.EAST.getCode());
        userGameRoomRepository.save(userGameRoomMapping);
        return gameRoom.getId();
    }

    @Override
    public List<UserGameRoomMapping> joinRoom(Long roomId, Long userId) throws Exception {
        //step1 check room
        GameRoom gameRoom = roomRepository.findById(roomId).orElseThrow(() -> new Exception("房间不存在"));
        if(RoomStateEnum.BEGIN.toString().equals(gameRoom.getState())){
            throw new Exception("房间已开始");
        }
        if(RoomStateEnum.END.toString().equals(gameRoom.getState())){
            throw new Exception("房间已结束");
        }
        //step2 分配剩余位置
        List<UserGameRoomMapping> userGameRoomMappings = userGameRoomRepository.findByRoomId(roomId);
        List<Integer> existLocation = userGameRoomMappings.stream().map(UserGameRoomMapping::getLocation).collect(Collectors.toList());
        List<Integer> missingLocations = LOCATION.stream().filter(loc -> !existLocation.contains(loc)).collect(Collectors.toList());
        if (!missingLocations.isEmpty()) {
            UserGameRoomMapping userGameRoomMapping = new UserGameRoomMapping();
            Random random = new Random();
            Integer randomLocation = missingLocations.get(random.nextInt(missingLocations.size()));
            userGameRoomMapping.setRoomId(roomId);
            userGameRoomMapping.setUserId(userId);
            userGameRoomMapping.setLocation(randomLocation);
            userGameRoomMapping.setScore(0L);
            userGameRoomRepository.save(userGameRoomMapping);
            userGameRoomMappings.add(userGameRoomMapping);
        } else {
            throw new Exception("房间已满");
        }

        return userGameRoomMappings;
    }

    @Override
    public List<UserGameRoomMapping> leaveRoom(Long roomId, Long userId) throws Exception {

        userGameRoomRepository.deleteByRoomAndUser(roomId,userId);

        List<UserGameRoomMapping> userGameRoomMappings = userGameRoomRepository.findByRoomId(roomId);

        GameRoom gameRoom = roomRepository.findById(roomId).orElseThrow(() -> new Exception("房间不存在"));
        //无人
        if(CollectionUtils.isEmpty(userGameRoomMappings)){
            gameRoom.setState(RoomStateEnum.END.toString());
            roomRepository.save(gameRoom);
            return null;
        }
        return userGameRoomMappings;

//        //进行中 全部结束  暂不考虑
//        if(RoomStateEnum.BEGIN.toString().equals(gameRoom.getState())){
//            gameRoom.setState(RoomStateEnum.END.toString());
//            roomRepository.save(gameRoom);
//        }
    }


    @Override
    public List<UserGameRoomMapping> ready(ReadyPlayerReq req, Long userId) {

        userGameRoomRepository.updatePlayerReady(req.getRoomId(),userId,req.isReady());

        return userGameRoomRepository.findByRoomId(req.getRoomId());
    }
}

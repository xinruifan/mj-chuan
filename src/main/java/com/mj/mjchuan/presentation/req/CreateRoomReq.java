package com.mj.mjchuan.presentation.req;

import lombok.Data;

import java.util.List;

/**
 * @author xinruifan
 * @create 2025-01-08 16:04
 */
@Data
public class CreateRoomReq {

    private List<Long> ruleId;

    private Integer totalRound;
}

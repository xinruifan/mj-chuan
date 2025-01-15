package com.mj.mjchuan.presentation.req;

import lombok.Data;

/**
 * @author xinruifan
 * @create 2025-01-10 15:39
 */
@Data
public class GamePlayerActionReq {

    private Long roundId;

    private Integer keyCard;

    private Long keyCardSource;

    private String uuid;

    private String action;

    private Long userId;
}

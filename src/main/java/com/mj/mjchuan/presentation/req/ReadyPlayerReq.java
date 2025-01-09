package com.mj.mjchuan.presentation.req;

import lombok.Data;

/**
 * @author xinruifan
 * @create 2025-01-09 13:35
 */
@Data
public class ReadyPlayerReq {

    private boolean ready;

    private Long roomId;
}

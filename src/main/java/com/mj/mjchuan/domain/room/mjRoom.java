package com.mj.mjchuan.domain.room;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author xinruifan
 * @create 2025-01-07 14:28
 */
@Data
public class mjRoom {

    private Long id;

    private String ruleId;

    private LocalDateTime createTime;


}

package com.mj.mjchuan.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xinruifan
 * @create 2025-01-09 10:52
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WsSendMsgDTO {

    private String action;

    private Object obj;

    private Long roundId;

}

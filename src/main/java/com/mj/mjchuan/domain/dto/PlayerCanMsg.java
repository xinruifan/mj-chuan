package com.mj.mjchuan.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xinruifan
 * @create 2025-01-10 14:42
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlayerCanMsg {

    private boolean chu;

    private boolean hu;

    private boolean pen;

    private boolean gang;

    private Integer keyCard;
}

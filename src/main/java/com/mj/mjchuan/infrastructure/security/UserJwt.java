package com.mj.mjchuan.infrastructure.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xinruifan
 * @create 2025-01-07 15:11
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserJwt {

    private Long id;

    private String account;

}

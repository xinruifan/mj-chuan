package com.mj.mjchuan.domain.user.model;

import lombok.Data;

import javax.persistence.*;

/**
 * @author xinruifan
 * @create 2025-01-07 14:59
 */
@Data
@Entity
@Table(name = "user_info")
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String account;

    private String password;
}

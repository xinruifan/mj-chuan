package com.mj.mjchuan.domain.user.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author xinruifan
 * @create 2025-01-07 14:59
 */
@Data
@Entity
@Table(name = "user_info")
public class UserInfo {

    @Id
    private Long id;

    private String account;

    private String password;
}

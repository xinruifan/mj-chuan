package com.mj.mjchuan.domain.game.enums;

import lombok.Getter;

/**
 * @author xinruifan
 * @create 2025-01-08 16:16
 */
@Getter
public enum RoomLocationEnum {

    EAST(1,"east"),
    SOUTH(2,"south"),
    WEST(3, "west"),
    NORTH(4,"north"),
    ;


    private final int code;
    private final String desc;

    RoomLocationEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    // 通过 code 获取对应的枚举
    public static RoomLocationEnum fromCode(int code) {
        for (RoomLocationEnum value : RoomLocationEnum.values()) {
            if (value.getCode() == code) {
                return value;
            }
        }
        throw new IllegalArgumentException("No enum found for code: " + code);
    }

    // 通过 desc 获取对应的枚举
    public static RoomLocationEnum fromDesc(String desc) {
        for (RoomLocationEnum value : RoomLocationEnum.values()) {
            if (value.getDesc().equals(desc)) {
                return value;
            }
        }
        throw new IllegalArgumentException("No enum found for desc: " + desc);
    }

}
